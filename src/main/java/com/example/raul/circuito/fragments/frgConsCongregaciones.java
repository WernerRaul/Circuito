package com.example.raul.circuito.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.raul.circuito.Adapters.AdapterDatosCongregaciones;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.GlobalClass;
import com.example.raul.circuito.R;
import com.example.raul.circuito.TabbedConsActivity;
import com.example.raul.circuito.Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgConsCongregaciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgConsCongregaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgConsCongregaciones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerConsCongregaciones;
    ArrayList<String> listaCongregaciones;
    ConexionSQLiteHelper conn;

    public frgConsCongregaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgConsCongregaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static frgConsCongregaciones newInstance(String param1, String param2) {
        frgConsCongregaciones fragment = new frgConsCongregaciones();
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

        View vista = inflater.inflate(R.layout.fragment_frg_cons_congregaciones, container, false);
        conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);
        listaCongregaciones=new ArrayList<>();

        recyclerConsCongregaciones=vista.findViewById(R.id.RecyclerConsCongId);
        recyclerConsCongregaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();

        AdapterDatosCongregaciones adapter = new AdapterDatosCongregaciones(listaCongregaciones);

        //escuchar que congregación del recycler se cliquea
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Selección del ID de congregación a partir del nombre y fecha de última visita
                SQLiteDatabase db=conn.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONGREGACIONES
                        +" WHERE "+ Utilidades.CAMPO_NOMBRE_CONGREGACION
                        + " = '" +listaCongregaciones.get(recyclerConsCongregaciones.getChildAdapterPosition(v))
                        +"'",null);
                cursor.moveToNext();
                Integer id=cursor.getInt(0); //id de congregación
                String fecha3=cursor.getString(3); //fecha de última visita
                cursor.close();
                db.close();
                conn.close();

                //id de Congregación registrado como variable global para ser leido por cualquier fragment
                GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();
                globalClass.setIdCongregacion(id);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaDate = null;
                try {
                    fechaDate = format.parse(fecha3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(fechaDate);
                calendar2.add(Calendar.MONTH,-5);
                String fecha2 = String.format("%1$tY-%1$tm-%1$td",calendar2.getTime());

                globalClass.setFechaA(fecha2);
                globalClass.setFechaB(fecha3);

                // Presentación de la información anterior
                Toast.makeText(getContext(),"Selección: "+ listaCongregaciones.get(recyclerConsCongregaciones.getChildAdapterPosition(v)) +
                        " fechas: " + globalClass.getFechaA() + " al " + globalClass.getFechaB(), Toast.LENGTH_LONG).show();

                //Creación de miIntent para pasar a Tabbed
                Intent miIntent = new Intent(getContext(),TabbedConsActivity.class);
                startActivity(miIntent);
            }
        });

        recyclerConsCongregaciones.setAdapter(adapter);

        return vista;
    }

    private void llenarLista() {

        SQLiteDatabase db=conn.getReadableDatabase();
        listaCongregaciones = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONGREGACIONES +
                " ORDER BY " + Utilidades.CAMPO_NOMBRE_CONGREGACION +
                " ASC", null); //llena el cursor en orden ascendente
        while (cursor.moveToNext()){
            listaCongregaciones.add(cursor.getString(1));
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
