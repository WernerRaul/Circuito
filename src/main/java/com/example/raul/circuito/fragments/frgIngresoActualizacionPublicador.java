package com.example.raul.circuito.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
 * {@link frgIngresoActualizacionPublicador.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoActualizacionPublicador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoActualizacionPublicador extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ConexionSQLiteHelper conn;
    Spinner spnCongregacion2;
    Spinner spnPublicador2;
    Spinner spnSexo;
    EditText edtNombrePublicador;
    EditText edtDireccion;
    EditText edtTelefono;
    EditText edtFechaNacimiento;
    EditText edtFechaBautizo;
    CheckBox chkAnciano;
    CheckBox chkSiervoMinisterial;
    CheckBox chkPrecursorRegular;
    EditText edtObservaciones;
    Button btnIngresarActualizacion;
    EditText edtUltimoRegistro;
    ArrayList<String> listaPublicadores;
    ArrayList<Publicadores> publicadoresList;
    Map<String,Integer> nombreCongMap;
    Map<String,Integer> nombrePubMap;
    String ID_Publicador;


    public frgIngresoActualizacionPublicador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoActualizacionPublicador.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoActualizacionPublicador newInstance(String param1, String param2) {
        frgIngresoActualizacionPublicador fragment = new frgIngresoActualizacionPublicador();
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
        View vista = inflater.inflate(R.layout.fragment_frg_ingreso_actualizacion_publicador, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        nombreCongMap = new HashMap<String, Integer>();
        nombrePubMap = new HashMap<String, Integer>();
        spnPublicador2 = vista.findViewById(R.id.spnPublicador2);
        edtNombrePublicador = vista.findViewById(R.id.edtNombrePublicador);
        edtDireccion = vista.findViewById(R.id.edtDireccion);
        edtTelefono = vista.findViewById(R.id.edtTelefono);
        edtFechaNacimiento = vista.findViewById(R.id.edtFechaNacimiento);
        edtFechaBautizo = vista.findViewById(R.id.edtFechaBautizo);
        chkAnciano = vista.findViewById(R.id.chkAnciano);
        chkSiervoMinisterial = vista.findViewById(R.id.chkSiervoMinisterial);
        chkPrecursorRegular = vista.findViewById(R.id.chkPrecursorRegular);
        edtObservaciones = vista.findViewById(R.id.edtObservaciones);
        spnSexo = vista.findViewById(R.id.spnSexo1);
        btnIngresarActualizacion = vista.findViewById(R.id.btnIngresarActualizacion);

        //Llenar spinner con nombre de las congregaciones
        spnCongregacion2 = vista.findViewById(R.id.spnCongregacion2);
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion2.setAdapter(adaptador);
        spnCongregacion2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incluirlista(); //llamar método
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Llenar spinner con sexos 'Hombre' y 'Mujer'
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.combo_sexo,android.R.layout.simple_spinner_item);
        spnSexo.setAdapter(adapter);

        btnIngresarActualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spnCongregacion2.getSelectedItemId() == 0 || spnPublicador2.getSelectedItemId() == 0 || edtNombrePublicador.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Rellene los campos arriba indicados",Toast.LENGTH_LONG).show();
                } else {
                    ejecutar();
                }
            }
        });

        return vista;
    }

    private void ejecutar(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"DATOS", null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        try {
            ContentValues values=new ContentValues();
            values.put("Nombre", edtNombrePublicador.getText().toString());
            values.put("Direccion", edtDireccion.getText().toString());
            values.put("Sexo", spnSexo.getSelectedItem().toString());
            values.put("Telefono", edtTelefono.getText().toString());
            values.put("FechaNacimiento", edtFechaNacimiento.getText().toString());
            values.put("FechaBautismo", edtFechaBautizo.getText().toString());
            values.put("Anciano", chkAnciano.isChecked() ? "true" : "false");
            values.put("SiervoMinisterial", chkSiervoMinisterial.isChecked() ? "true" : "false");
            values.put("PrecRegular", chkPrecursorRegular.isChecked() ? "true" : "false");
            values.put("Observaciones", edtObservaciones.getText().toString());
            values.put("ID_Congregacion",nombreCongMap.get(spnCongregacion2.getSelectedItem().toString()));

            String[] ID = new String[] {String.valueOf(ID_Publicador)};
            int a = db.update("tbl_PUBLICADORES", values,"ID_Publicador=?",ID);
            if (a == 1) {
                Toast.makeText(getContext(),"Se actualizó en la base de datos. Nombre: " +
                        edtNombrePublicador.getText().toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),"No se actualizó en la base de datos", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            Toast.makeText(getContext(),"No se actualizó en la base de datos. Error: " + e, Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void incluirlista() {
        //método para incluir la lista de publicadores de la congregacion deseada

        consultarlistaPublicadores(); //llamar método
        ArrayAdapter adapter = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaPublicadores);
        spnPublicador2.setAdapter(adapter);
        spnPublicador2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    datoseditar();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void datoseditar() {
        //recopilamos datos del nombre seleccionado en el spinner
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES + " WHERE Nombre = '" +
                spnPublicador2.getSelectedItem() + "'", null);
        cursor.moveToNext();

        //VISUALIZACIÓN DE DATOS QUE OFRECE EL CURSOR EN LOS VIEW
        ID_Publicador = cursor.getString(0);
        edtNombrePublicador.setText(cursor.getString(1));
        edtDireccion.setText(cursor.getString(2));
        //establecer valor en campo Sexo
        if (cursor.getString(3).equals("Hombre")){
            spnSexo.setSelection(0);
        } else {
            spnSexo.setSelection(1);
        }
        edtTelefono.setText(cursor.getString(4));
        edtFechaNacimiento.setText(cursor.getString(5));
        edtFechaBautizo.setText(cursor.getString(6));
        chkAnciano.setChecked(Boolean.parseBoolean(cursor.getString(7)));
        chkSiervoMinisterial.setChecked(Boolean.parseBoolean(cursor.getString(8)));
        chkPrecursorRegular.setChecked(Boolean.parseBoolean(cursor.getString(9)));
        edtObservaciones.setText(cursor.getString(11));

        db.close();

    }

    private void consultarlistaPublicadores() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Publicadores publicador = null;
        publicadoresList = new ArrayList<Publicadores>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE ID_Congregacion = " + nombreCongMap.get(spnCongregacion2.getSelectedItem().toString()) +
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
