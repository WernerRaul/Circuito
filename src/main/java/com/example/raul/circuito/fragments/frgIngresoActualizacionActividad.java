package com.example.raul.circuito.fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raul.circuito.Adapters.AdapterDatosActualizacionActividad;
import com.example.raul.circuito.Clases.ActividadVo;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.Clases.Publicadores;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgIngresoActualizacionActividad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoActualizacionActividad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoActualizacionActividad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spnCongregacion;
    Spinner spnPublicador;
    ConexionSQLiteHelper conn;
    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ArrayList<String> listaPublicadores;
    ArrayList<Publicadores> publicadoresList;
    Map<String,Integer> nombreCongMap;
    Map<String,Integer> nombrePubMap;
    RecyclerView recyclerActividad;
    ArrayList<ActividadVo> listaActividad;

    public frgIngresoActualizacionActividad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoActualizacionActividad.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoActualizacionActividad newInstance(String param1, String param2) {
        frgIngresoActualizacionActividad fragment = new frgIngresoActualizacionActividad();
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
        final View vista = inflater.inflate(R.layout.fragment_frg_ingreso_actualizacion_actividad, container, false);

        listaActividad = new ArrayList<>();
        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        nombreCongMap = new HashMap<String, Integer>();
        nombrePubMap = new HashMap<String, Integer>();

        recyclerActividad = vista.findViewById(R.id.RecyclerActualizacionActividad);
        recyclerActividad.setLayoutManager(new LinearLayoutManager(getContext()));

        spnPublicador = vista.findViewById(R.id.spnPublicador7);

        //adaptador para recyclerview
        final AdapterDatosActualizacionActividad adapter = new AdapterDatosActualizacionActividad(listaActividad);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //DIALOGO DE ELECCION//
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array); //Opciones en radiobutton
                int itemSelected = 0;
                final int[] item = {0}; //primer item del radiobutton "editar"

                //muestra ID seleccionado
                builder.setTitle("Elegir Acción para ID: "+listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).getID());

                //elección de radiobutton
                builder.setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1){
                            item[0] = 1;
                        } else {
                            item[0] = 0;
                        }
                    }
                });

                //al hacer click en OK
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Lectura del ID_Actividad para rellenar los editviews
                        SQLiteDatabase db = conn.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT Horas, Revisitas, Estudios, PAuxiliar " +
                                "FROM tbl_ACTIVIDAD " +
                                "where ID_Actividad = '" + listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).getID() + "'", null);
                        cursor.moveToNext();

                        if (item[0] == 1){
                            //si se elige remover
                            //borrar fila de base de datos
                            SQLiteDatabase dbd=conn.getWritableDatabase();
                            dbd.delete("tbl_ACTIVIDAD","ID_Actividad=?",new String[]{String.valueOf(listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).getID())});
                            Toast.makeText(getContext(),"Fila " + listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).getID() + "REMOVIDA!!!", Toast.LENGTH_LONG).show();

                            //actualizar recycler
                            listaActividad.remove(recyclerActividad.getChildAdapterPosition(v));
                            adapter.notifyItemRemoved(recyclerActividad.getChildAdapterPosition(v));

                        } else {

                            //si se elige editar
                            //creación de diálogo de edición
                            final Dialog customDialog = new Dialog(getContext(),R.style.Theme_Dialog_Translucent);
                            customDialog.setContentView(R.layout.input_dialog); //seteamos del diálogo personalizado
                            final EditText edHoras = customDialog.findViewById(R.id.edHoras);
                            final EditText edRevisitas = customDialog.findViewById(R.id.edRevisitas);
                            final EditText edEstudios = customDialog.findViewById(R.id.edEstudios);
                            final EditText edAuxiliar = customDialog.findViewById(R.id.edAuxiliar);
                            Button btnOK = customDialog.findViewById(R.id.btnOk);
                            Button btnCancel = customDialog.findViewById(R.id.btnCancel);

                            //mostrar el id seleccionado
                            edHoras.setText(cursor.getString(0));
                            edRevisitas.setText(cursor.getString(1));
                            edEstudios.setText(cursor.getString(2));
                            edAuxiliar.setText(cursor.getString(3));
                            customDialog.show();

                            btnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View x) {
                                SQLiteDatabase db=conn.getWritableDatabase();
                                //Actualizacion de Base de datos
                                try {
                                    ContentValues values=new ContentValues();
                                    values.put("Horas", Integer.parseInt(edHoras.getText().toString()));
                                    values.put("Revisitas", Integer.parseInt(edRevisitas.getText().toString()));
                                    values.put("Estudios", Integer.parseInt(edEstudios.getText().toString()));
                                    values.put("PAuxiliar",edAuxiliar.getText().toString());

                                    String[] ID = new String[] {String.valueOf(listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).getID())};
                                    int a = db.update("tbl_ACTIVIDAD", values,"ID_Actividad=?",ID);
                                    if (a == 1) {
                                        Toast.makeText(getContext(),"Se actualizó en la base de datos. ID: " +
                                                listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).getID(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(),"No se actualizó en la base de datos", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e){
                                    Toast.makeText(getContext(),"No se actualizó en la base de datos. Error: " + e, Toast.LENGTH_LONG).show();
                                }

                                //Actualización de recycler
                                listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).setHoras(edHoras.getText().toString());
                                listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).setRevisitas(edRevisitas.getText().toString());
                                listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).setEstudios(edEstudios.getText().toString());
                                listaActividad.get(recyclerActividad.getChildAdapterPosition(v)).setAuxiliar(edAuxiliar.getText().toString());

                                adapter.notifyItemChanged(recyclerActividad.getChildAdapterPosition(v));
                                customDialog.hide();

                                }
                            });

                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.hide();
                                }
                            });

                        }
                    }
                });

                builder.show();

            }
        });

        consultarlistaCongregaciones(); //llamar al método comenzamos a poblar el spinner Congregaciones
        spnCongregacion = vista.findViewById(R.id.spnCongregacion7);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);

        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incluirlista();//llamarmétodo
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnPublicador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),spnPublicador.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                llenarLista();


                adapter.notifyDataSetChanged();

                recyclerActividad.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return vista;
    }

    private void llenarLista() {
        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_Actividad, AñoMes, Horas, Revisitas, Estudios, PAuxiliar " +
                "FROM tbl_PUBLICADORES, tbl_ACTIVIDAD " +
                "where tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                "and tbl_PUBLICADORES.Nombre = '" + spnPublicador.getSelectedItem().toString() + "'", null);

        listaActividad.clear(); //vaciamos si tenemos datos previos

        while (cursor.moveToNext()) {
            ActividadVo actividadVo = new ActividadVo();
            actividadVo.setID(cursor.getString(0));
            actividadVo.setAnoMes(cursor.getString(1));
            actividadVo.setHoras(cursor.getString(2));
            actividadVo.setRevisitas(cursor.getString(3));
            actividadVo.setEstudios(cursor.getString(4));
            actividadVo.setAuxiliar(cursor.getString(5));

            listaActividad.add(actividadVo);
        }

        cursor.close();
        db.close();
        conn.close();

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
        listaCongregaciones.add("Seleccione");

        for (int i = 0; i < congregacionesList.size(); i++) {
            listaCongregaciones.add(congregacionesList.get(i).getnombre());
        }

    }

    private void incluirlista() {
        //método para incluir la lista de publicadores de la congregacion deseada

        consultarlistaPublicadores(); //llamar método
        ArrayAdapter adapter = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaPublicadores);
        spnPublicador.setAdapter(adapter);
    }

    private void consultarlistaPublicadores() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Publicadores publicador = null;
        publicadoresList = new ArrayList<Publicadores>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE ID_Congregacion = " + nombreCongMap.get(spnCongregacion.getSelectedItem().toString()) +
                " ORDER BY "+ Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_Nombre_Publicador +
                " ASC", null);
        while (cursor.moveToNext()) {
            publicador = new Publicadores();
            publicador.setNombre(cursor.getString(1));
            nombrePubMap.put(cursor.getString(1),cursor.getInt(0)); //map para guardar id de publicador que se usará para ingresar a la bd actividad
            publicadoresList.add(publicador);
        }
        db.close();
        obtenerlistaP(); //llamar método

    }

    private void obtenerlistaP() {
        listaPublicadores = new ArrayList<String>();
        listaPublicadores.add("Seleccione");

        for (int i = 0; i < publicadoresList.size(); i++) {
            listaPublicadores.add(publicadoresList.get(i).getNombre());
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
