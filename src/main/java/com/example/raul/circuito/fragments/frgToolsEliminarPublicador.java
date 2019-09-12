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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.raul.circuito.Adapters.AdapterDatosCongregaciones;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgToolsEliminarPublicador.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgToolsEliminarPublicador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgToolsEliminarPublicador extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spnCongregacion;
    ConexionSQLiteHelper conn;
    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    Map<String,Integer> nombreCongMap;
    RecyclerView recyclerConsPublicadores;
    ArrayList<String> listaPublicadores;


    public frgToolsEliminarPublicador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgToolsEliminarPublicador.
     */
    // TODO: Rename and change types and number of parameters
    public static frgToolsEliminarPublicador newInstance(String param1, String param2) {
        frgToolsEliminarPublicador fragment = new frgToolsEliminarPublicador();
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
        View vista = inflater.inflate(R.layout.fragment_frg_tools_eliminar_publicador, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);
        nombreCongMap = new HashMap<String, Integer>();

        consultarlistaCongregaciones(); //llamar al método comenzamos a poblar el spinner Congregaciones

        spnCongregacion = vista.findViewById(R.id.spnCongregacion8);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);
        listaPublicadores = new ArrayList<>();

        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llenarlista();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerConsPublicadores = vista.findViewById(R.id.RecyclerConsPubId1);
        recyclerConsPublicadores.setLayoutManager(new LinearLayoutManager(getContext()));
//        AdapterDatosCongregaciones adapter = new AdapterDatosCongregaciones(listaPublicadores);
//        recyclerConsPublicadores.setAdapter(adapter);


/*

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array); //Opciones en radiobutton

                builder.setTitle("¡¡¡ALERTA!!!");
                builder.setMessage(" ¿Está seguro que quiere eliminar al Publicador "
                        +listaCongregaciones.get(recyclerConsPublicadores.getChildAdapterPosition(v))
                        + " con toda su actividad relacionada?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db=conn.getWritableDatabase();
                        db.execSQL("PRAGMA foreign_keys = ON;"); //habilitar borrado en cascada
                        db.execSQL("DELETE FROM " + Utilidades.TABLA_PUBLICADORES + " WHERE "
                                + Utilidades.CAMPO_Nombre_Publicador + " = '"
                                + listaCongregaciones.get(recyclerConsPublicadores.getChildAdapterPosition(v)) + "'");
                        db.close();

                        //actualizar recycler
                        Toast.makeText(getContext(),"Publicador " + listaCongregaciones.get(recyclerConsPublicadores.getChildAdapterPosition(v)) + " REMOVIDO!!!", Toast.LENGTH_LONG).show();

                        listaCongregaciones.remove(recyclerConsPublicadores.getChildAdapterPosition(v));
                        adapter.notifyItemRemoved(recyclerConsPublicadores.getChildAdapterPosition(v));
                        recyclerConsPublicadores.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
*/

        return vista;
    }

    private void llenarlista() {
        listaPublicadores=new ArrayList<>();
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PUBLICADORES
                +" WHERE "+ Utilidades.CAMPO_ID_CONGREGACION + " = " +nombreCongMap.get(spnCongregacion.getSelectedItem().toString())+
                " ORDER BY "+ Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_Nombre_Publicador +
                " ASC", null); //llenar el cursor en orden ascendente

        while (cursor.moveToNext()){
            listaPublicadores.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        conn.close();
        AdapterDatosCongregaciones adapter = new AdapterDatosCongregaciones(listaPublicadores);
        recyclerConsPublicadores.setAdapter(adapter);

    }

    private void consultarlistaCongregaciones() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Congregaciones congregacion = null;
        congregacionesList = new ArrayList<Congregaciones>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONGREGACIONES, null);
        while (cursor.moveToNext()) {
            congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1)); //Ingresar nombre de la congregacion
            nombreCongMap.put(cursor.getString(1),cursor.getInt(0)); //nombre de congregación e Id para usarlo en método ejecutar
            congregacionesList.add(congregacion);
        }
        db.close();
        obtenerlista(); //llamar método

    }

    private void obtenerlista() {
        listaCongregaciones = new ArrayList<String>();
        listaCongregaciones.add("Seleccione Congregación...");
        for (int i = 0; i < congregacionesList.size(); i++) {
            listaCongregaciones.add(congregacionesList.get(i).getnombre());
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
