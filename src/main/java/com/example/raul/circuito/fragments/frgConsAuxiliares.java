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

import com.example.raul.circuito.Adapters.AdapterDatosAuxiliares;
import com.example.raul.circuito.Clases.AuxiliarVo;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.GlobalClass;
import com.example.raul.circuito.GraficoActivity;
import com.example.raul.circuito.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgConsAuxiliares.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgConsAuxiliares#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgConsAuxiliares extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ConexionSQLiteHelper conn;
    RecyclerView recyclerConsAuxiliares;
    ArrayList<AuxiliarVo> listaAuxiliares;

    public frgConsAuxiliares() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgConsAuxiliares.
     */
    // TODO: Rename and change types and number of parameters
    public static frgConsAuxiliares newInstance(String param1, String param2) {
        frgConsAuxiliares fragment = new frgConsAuxiliares();
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
        
        View vista = inflater.inflate(R.layout.fragment_frg_cons_auxiliares, container, false);
        
        listaAuxiliares = new ArrayList<>();
        
        recyclerConsAuxiliares = vista.findViewById(R.id.RecyclerConsAuxiliares);
        recyclerConsAuxiliares.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista();

        AdapterDatosAuxiliares adapter = new AdapterDatosAuxiliares(listaAuxiliares);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nombre de publicador registrado como variable global para ser leido por cualquier fragment
                GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();
                globalClass.setNombrePublicador(listaAuxiliares.get(recyclerConsAuxiliares.getChildAdapterPosition(v)).getNombre());

                Intent miIntent = new Intent(getContext(),GraficoActivity.class);
                startActivity(miIntent);

            }
        });
        recyclerConsAuxiliares.setAdapter(adapter);
        
        return vista;
    }

    private void llenarlista() {

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();
        String [] argumentos = {String.valueOf(globalClass.getIdCongregacion()),
            globalClass.getFechaA(), globalClass.getFechaB()};

        builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                "AND tbl_ACTIVIDAD.PAuxiliar = 'true')"); //+
        String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                "tbl_ACTIVIDAD.Horas",
                "tbl_ACTIVIDAD.Revisitas",
                "tbl_ACTIVIDAD.Estudios",
                "tbl_ACTIVIDAD.AñoMes"};
        Cursor cursor = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.ID_Congregacion = ? and (tbl_ACTIVIDAD.AñoMes between " +
                "? and ?)", argumentos, null, null, null);

        while (cursor.moveToNext()) {
            AuxiliarVo auxiliarVo = new AuxiliarVo();
            auxiliarVo.setNombre(cursor.getString(0));
            auxiliarVo.setHoras(cursor.getString(1));
            auxiliarVo.setRevisitas(cursor.getString(2));
            auxiliarVo.setEstudios(cursor.getString(3));
            auxiliarVo.setAñomes(cursor.getString(4));

            listaAuxiliares.add(auxiliarVo);
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
