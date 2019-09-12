package com.example.raul.circuito.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raul.circuito.Adapters.AdapterDatosVarones;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.GlobalClass;
import com.example.raul.circuito.GraficoActivity;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;
import com.example.raul.circuito.Clases.VaronVo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgConsVarones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgConsVarones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgConsVarones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ConexionSQLiteHelper conn;
    RecyclerView recyclerConsVarones;
    ArrayList<VaronVo> listaVarones;

    public frgConsVarones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgConsVarones.
     */
    // TODO: Rename and change types and number of parameters
    public static frgConsVarones newInstance(String param1, String param2) {
        frgConsVarones fragment = new frgConsVarones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_frg_cons_varones,container,false);

        listaVarones = new ArrayList<>();

        recyclerConsVarones = vista.findViewById(R.id.RecyclerConsVarones);
        recyclerConsVarones.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();

        AdapterDatosVarones adapter = new AdapterDatosVarones(listaVarones);
        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //nombre de publicador registrado como variable global para ser leido por cualquier fragment
                GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();
                globalClass.setNombrePublicador(listaVarones.get(recyclerConsVarones.getChildAdapterPosition(v)).getNombre());

                Intent miIntent = new Intent(getContext(),GraficoActivity.class);
                startActivity(miIntent);

            }
        });

        recyclerConsVarones.setAdapter(adapter);

        return vista;
    }

    private void llenarLista() {

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();
        String [] argumentos = {String.valueOf(globalClass.getIdCongregacion()),
            globalClass.getFechaA(), globalClass.getFechaB()};

        
        builder.setTables(Utilidades.TABLA_PUBLICADORES + " INNER JOIN " + Utilidades.TABLA_CONGREGACIONES + " ON " +
                "(" + Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_ID_CONGREGACION + " = " + Utilidades.TABLA_CONGREGACIONES + "." + Utilidades.CAMPO_ID_CONGREGACION +
                " AND " + Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_Sexo + " = 'Hombre')" +
                " INNER JOIN " + Utilidades.TABLA_ACTIVIDAD + " ON (" + Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_ID_PUBLICADOR + " = " + Utilidades.TABLA_ACTIVIDAD + "." + Utilidades.CAMPO_ID_PUBLICADOR + ")");
        String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                "strftime('%Y','now')- strftime('%Y',tbl_PUBLICADORES.FechaNacimiento) as Edad",
                "strftime('%Y','now')- strftime('%Y',tbl_PUBLICADORES.FechaBautismo) as Bautizado",
                "tbl_PUBLICADORES.Anciano",
                "tbl_PUBLICADORES.SiervoMinisterial",
                "tbl_PUBLICADORES.PrecRegular",
                "round(AVG(tbl_ACTIVIDAD.Horas),1) as PromHoras",
                "round(AVG(tbl_ACTIVIDAD.Revisitas),1) as PromRevisitas",
                "round(AVG(tbl_ACTIVIDAD.Estudios),1) as PromEstudios"};
        String groupBy = "tbl_PUBLICADORES.Nombre, " +
                "tbl_PUBLICADORES.Anciano, " +
                "tbl_PUBLICADORES.SiervoMinisterial,  " +
                "tbl_PUBLICADORES.PrecRegular,  " +
                "tbl_PUBLICADORES.FechaNacimiento, " +
                "tbl_PUBLICADORES.FechaBautismo,  " +
                "tbl_PUBLICADORES.Sexo";
        Cursor cursor = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.ID_Congregacion = ? and (tbl_ACTIVIDAD.AñoMes between ? and ?)",
                argumentos, groupBy, null, "AVG(tbl_ACTIVIDAD.Horas) DESC");

        while (cursor.moveToNext()) {
            VaronVo varonVo= new VaronVo();
            varonVo.setNombre(cursor.getString(0));
            varonVo.setEdad(cursor.getString(1));
            varonVo.setBautizado(cursor.getString(2));
            varonVo.setAnciano(cursor.getString(3));
            varonVo.setSm(cursor.getString(4));
            varonVo.setPrecregular(cursor.getString(5));
            varonVo.setHoras(cursor.getString(6));
            varonVo.setRevisitas(cursor.getString(7));
            varonVo.setEstudios(cursor.getString(8));

            listaVarones.add(varonVo);
        }

        cursor.close();
        db.close();
        conn.close();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
