package com.example.raul.circuito.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.raul.circuito.Adapters.AdapterDatosCongregaciones;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.GlobalClass;
import com.example.raul.circuito.GraficoActivity;
import com.example.raul.circuito.R;
import com.example.raul.circuito.TabbedConsActivity;
import com.example.raul.circuito.Utilidades;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgConsPublicadores.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgConsPublicadores#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgConsPublicadores extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerConsPublicadores;
    ArrayList<String> listaPublicadores;
    ConexionSQLiteHelper conn;


    public frgConsPublicadores() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgConsPublicadores.
     */
    // TODO: Rename and change types and number of parameters
    public static frgConsPublicadores newInstance(String param1, String param2) {
        frgConsPublicadores fragment = new frgConsPublicadores();
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

        View vista = inflater.inflate(R.layout.fragment_frg_cons_publicadores,container,false);
        conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);
        listaPublicadores = new ArrayList<>();

        recyclerConsPublicadores = vista.findViewById(R.id.RecyclerConsPubId);
        recyclerConsPublicadores.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista();

        AdapterDatosCongregaciones adapter = new AdapterDatosCongregaciones(listaPublicadores);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nombre de publicador registrado como variable global para ser leido por cualquier fragment
                GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();
                globalClass.setNombrePublicador(listaPublicadores.get(recyclerConsPublicadores.getChildAdapterPosition(v)));

                Intent miIntent = new Intent(getContext(),GraficoActivity.class);
                startActivity(miIntent);
            }
        });


        recyclerConsPublicadores.setAdapter(adapter);

        return vista;
    }

    private void llenarlista() {
        listaPublicadores=new ArrayList<>();

        //leemos el id de Congregación cliqueado en frgConsCongregacion y registrado como variable global
        GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();

        //Selección del ID de congregación a partir del nombre
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PUBLICADORES
                +" WHERE "+ Utilidades.CAMPO_ID_CONGREGACION + " = " + globalClass.getIdCongregacion() +
                " ORDER BY "+ Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_Nombre_Publicador +
                " ASC", null); //llenar el cursor en orden ascendente
        while (cursor.moveToNext()){
            listaPublicadores.add(cursor.getString(1));
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
