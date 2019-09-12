package com.example.raul.circuito.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.raul.circuito.Adapters.AdapterDatosPublicadores;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgPublicadorNoActualizado.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgPublicadorNoActualizado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgPublicadorNoActualizado extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ConexionSQLiteHelper conn;
    Spinner spnCongregacion;
    ArrayList<Congregaciones> congregacionesList;
    ArrayList<String> listaCongregaciones;
    RecyclerView recyclerConsPublicadores;
    Button boton;
    List<String> sourceList;

    public frgPublicadorNoActualizado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgPublicadorNoActualizado.
     */
    // TODO: Rename and change types and number of parameters
    public static frgPublicadorNoActualizado newInstance(String param1, String param2) {
        frgPublicadorNoActualizado fragment = new frgPublicadorNoActualizado();
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
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_frg_publicador_no_actualizado, container, false);
        conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);
        spnCongregacion = vista.findViewById(R.id.spnCongregacion);
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);
        recyclerConsPublicadores = vista.findViewById(R.id.RecyclerConsPub1);
        recyclerConsPublicadores.setLayoutManager(new LinearLayoutManager(getContext()));
        boton = vista.findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgPublicadorNoActualizado.this.metodo();
                frgPublicadorNoActualizado.this.recyclerConsPublicadores.setAdapter(new AdapterDatosPublicadores((ArrayList) frgPublicadorNoActualizado.this.sourceList));
            }
        });

        return vista;
    }

    private void metodo() {
        SQLiteDatabase db1 = this.conn.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT tbl_PUBLICADORES.Nombre, tbl_CONGREGACIONES.Observaciones FROM tbl_CONGREGACIONES, tbl_PUBLICADORES WHERE tbl_CONGREGACIONES.Id_CONGREGACION = tbl_PUBLICADORES.Id_CONGREGACION AND tbl_CONGREGACIONES.Nombre = '");
        sb.append(this.spnCongregacion.getSelectedItem());
        String str = "'";
        sb.append(str);
        Cursor cursor1 = db1.rawQuery(sb.toString(), null);
        ArrayList<String> publicadoresActuales = new ArrayList<>();
        String fecha = null;
        while (cursor1.moveToNext()) {
            publicadoresActuales.add(cursor1.getString(0));
            fecha = cursor1.getString(1);
        }
        SQLiteDatabase db2 = this.conn.getReadableDatabase();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT tbl_PUBLICADORES.Nombre FROM tbl_CONGREGACIONES, tbl_PUBLICADORES, tbl_ACTIVIDAD WHERE tbl_CONGREGACIONES.Id_CONGREGACION = tbl_PUBLICADORES.Id_CONGREGACION AND tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador AND tbl_CONGREGACIONES.Nombre = '");
        sb2.append(this.spnCongregacion.getSelectedItem());
        sb2.append("' AND ");
        sb2.append(Utilidades.TABLA_ACTIVIDAD);
        sb2.append(".");
        sb2.append(Utilidades.CAMPO_AñoMes);
        sb2.append(" = '");
        sb2.append(fecha);
        sb2.append(str);
        Cursor cursor2 = db2.rawQuery(sb2.toString(), null);
        ArrayList<String> publicadoresInformado = new ArrayList<>();
        while (cursor2.moveToNext()) {
            publicadoresInformado.add(cursor2.getString(0));
        }
        ArrayList arrayList = publicadoresInformado;
        this.sourceList = new ArrayList(publicadoresActuales);
        this.sourceList.removeAll(arrayList);
    }

    private void consultarlistaCongregaciones() {
        SQLiteDatabase db = this.conn.getReadableDatabase();
        this.congregacionesList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_CONGREGACIONES", null);
        while (cursor.moveToNext()) {
            Congregaciones congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1));
            this.congregacionesList.add(congregacion);
        }
        obtenerlista();
    }

    private void obtenerlista() {
        this.listaCongregaciones = new ArrayList<>();
        this.listaCongregaciones.add("Seleccione");
        for (int i = 0; i < this.congregacionesList.size(); i++) {
            this.listaCongregaciones.add(((Congregaciones) this.congregacionesList.get(i)).getnombre());
        }
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
