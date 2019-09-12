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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * {@link frgIngresoNuevoPublicador.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoNuevoPublicador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoNuevoPublicador extends Fragment {
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
    Spinner spnCongregacion;
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
    Button btnIngresarPublicador;
    TextView lblUltimoRegistro;
    Map<String,Integer> nombreMap;


    public frgIngresoNuevoPublicador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoNuevoPublicador.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoNuevoPublicador newInstance(String param1, String param2) {
        frgIngresoNuevoPublicador fragment = new frgIngresoNuevoPublicador();
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
        View vista = inflater.inflate(R.layout.fragment_frg_ingreso_nuevo_publicador, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);

        edtNombrePublicador = vista.findViewById(R.id.edtNombrePublicador);
        edtDireccion = vista.findViewById(R.id.edtDireccion);
        edtTelefono = vista.findViewById(R.id.edtTelefono);
        edtFechaNacimiento = vista.findViewById(R.id.edtFechaNacimiento);
        edtFechaBautizo = vista.findViewById(R.id.edtFechaBautizo);
        chkAnciano = vista.findViewById(R.id.chkAnciano);
        chkSiervoMinisterial = vista.findViewById(R.id.chkSiervoMinisterial);
        chkPrecursorRegular = vista.findViewById(R.id.chkPrecursorRegular);
        edtObservaciones = vista.findViewById(R.id.edtObservaciones);
        lblUltimoRegistro = vista.findViewById(R.id.lblUltimoRegistro);
        nombreMap = new HashMap<String, Integer>();

        //Llenar spinner con nombre de las congregaciones
        spnCongregacion = vista.findViewById(R.id.spnCongregacion1);
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);

        //Llenar spinner con sexos 'Hombre' y 'Mujer'
        spnSexo = vista.findViewById(R.id.spnSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.combo_sexo,android.R.layout.simple_spinner_item);
        spnSexo.setAdapter(adapter);

        //Hacer evento click en button
        btnIngresarPublicador = vista.findViewById(R.id.btnIngresarPublicador);
        btnIngresarPublicador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((spnCongregacion.getSelectedItemId() == 0) || edtNombrePublicador.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Rellene los campos", Toast.LENGTH_LONG).show();
                } else {
                    ejecutar();
                }

            }
        });

        //Cuando button tiene el foco cambia de color a rojo
        btnIngresarPublicador.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnIngresarPublicador.setBackgroundColor(0xFFE51A4C);
            }
        });

        return vista;
    }

    private void ejecutar() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"DATOS", null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        //Para información de último registro incluido al final del fragment
        String UltimoRegistro = edtNombrePublicador.getText().toString();

        //Registrar
        try {
            ContentValues values=new ContentValues();
            values.putNull("ID_Publicador");
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
            values.put("ID_Congregacion",nombreMap.get(spnCongregacion.getSelectedItem().toString()));

            int a = (int) db.insert("tbl_PUBLICADORES","Observaciones", values); //para información en el Toast
            Toast.makeText(getContext(),"Se registró en la base de datos. Número de registro "+ a, Toast.LENGTH_LONG).show();
            limpiar(); //limpiar EditTexts
        }catch (Exception e) {
            Toast.makeText(getContext(),"No se registró en la base de datos", Toast.LENGTH_LONG).show();
        }
        db.close();

        lblUltimoRegistro.setText("Último registro: " +UltimoRegistro); //Información de Último registro
        edtNombrePublicador.requestFocus(); //foco a edtNombre
        btnIngresarPublicador.setBackgroundColor(0x00000000); //boton de color blanco
    }

    private void limpiar() {
        // todos los editores al estado original
        edtNombrePublicador.setText("");
        edtDireccion.setText("");
        edtTelefono.setText("");
        spnSexo.setSelection(0);
        edtFechaNacimiento.setText("");
        edtFechaBautizo.setText("");
        chkAnciano.setChecked(false);
        chkPrecursorRegular.setChecked(false);
        chkSiervoMinisterial.setChecked(false);
        edtObservaciones.setText("");
    }

     private void consultarlistaCongregaciones() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Congregaciones congregacion = null;

        congregacionesList = new ArrayList<Congregaciones>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONGREGACIONES, null);
        while (cursor.moveToNext()) {
            congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1)); //Ingresar nombre de la congregacion
            nombreMap.put(cursor.getString(1),cursor.getInt(0)); //nombre de congregación e Id para usarlo en método ejecutar
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
