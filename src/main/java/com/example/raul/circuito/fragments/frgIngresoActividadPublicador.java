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
import android.widget.TextView;
import android.widget.Toast;

import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.Clases.Publicadores;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgIngresoActividadPublicador.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoActividadPublicador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoActividadPublicador extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spnCongregacion5;
    Spinner spnPublicador5;
    EditText edtFecha;
    EditText edtHoras;
    EditText edtRevisitas;
    EditText edtEstudios;
    CheckBox chkPrecursorAuxiliar;
    EditText edtObservaciones;
    Button btnIngresarActividad;
    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ArrayList<String> listaPublicadores;
    ArrayList<Publicadores> publicadoresList;
    Map<String,Integer> nombreCongMap;
    Map<String,Integer> nombrePubMap;
    TextView lblUltimoRegistro;

    ConexionSQLiteHelper conn;


    public frgIngresoActividadPublicador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoActividadPublicador.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoActividadPublicador newInstance(String param1, String param2) {
        frgIngresoActividadPublicador fragment = new frgIngresoActividadPublicador();
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
        View vista = inflater.inflate(R.layout.fragment_frg_ingreso_actividad_publicador, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);
        nombreCongMap = new HashMap<String, Integer>();
        nombrePubMap = new HashMap<String, Integer>();

        spnPublicador5 = vista.findViewById(R.id.spnPublicador5);
        spnCongregacion5 = vista.findViewById(R.id.spnCongregacion5);
        edtFecha = vista.findViewById(R.id.edtFecha);
        edtHoras = vista.findViewById(R.id.edtHoras);
        edtRevisitas = vista.findViewById(R.id.edtRevisitas);
        edtEstudios = vista.findViewById(R.id.edtEstudios);
        chkPrecursorAuxiliar = vista.findViewById(R.id.chkPrecursorAuxiliar);
        edtObservaciones = vista.findViewById(R.id.edtObservaciones);
        lblUltimoRegistro = vista.findViewById(R.id.lblUltimoRegistro);


        //Llenar spinner con nombre de las congregaciones
        spnCongregacion5 = vista.findViewById(R.id.spnCongregacion5);
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion5.setAdapter(adaptador);
        spnCongregacion5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incluirlista(); //llamar método
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnIngresarActividad = vista.findViewById(R.id.btnIngresarActividad);
        btnIngresarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((spnCongregacion5.getSelectedItemId() == 0) || (spnPublicador5.getSelectedItemId() == 0) ||
                        edtFecha.getText().toString().equals("") || edtHoras.getText().toString().equals("") ||
                        edtRevisitas.getText().toString().equals("") || edtEstudios.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Rellene todos los campos",Toast.LENGTH_LONG).show();
                } else {
                    ejecutar();
                }

            }
        });

        btnIngresarActividad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnIngresarActividad.setBackgroundColor(0xFFE51A4C);
            }
        });
        return vista;
    }

    private void ejecutar() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Horas", edtHoras.getText().toString());
            values.put("Revisitas", edtRevisitas.getText().toString());
            values.put("Estudios", edtEstudios.getText().toString());
            values.put("PAuxiliar", chkPrecursorAuxiliar.isChecked() ? "true" : "false");
            values.put("Observaciones", edtObservaciones.getText().toString());
            values.put("AñoMes", edtFecha.getText().toString());
            values.put("Id_Publicador", nombrePubMap.get(spnPublicador5.getSelectedItem().toString())); //utilizamos el map

            int a = (int) db.insert("tbl_ACTIVIDAD","Observaciones", values);
            Toast.makeText(getContext(),"Se registró en la base de datos. Número de registro "+ a, Toast.LENGTH_LONG).show();
            String u = "Última fecha registrada: " + edtFecha.getText().toString()
                    + ", Horas: " + edtHoras.getText().toString() + ", Revisitas: " + edtRevisitas.getText().toString()
                    + ", Estudios: " + edtEstudios.getText().toString() + ", ID: " + nombrePubMap.get(spnPublicador5.getSelectedItem().toString());
            lblUltimoRegistro.setText(u);
            limpiar(); //llamar método
        } catch (Exception e) {
            Toast.makeText(getContext(),"No se registró en la base de datos. Error: " + e, Toast.LENGTH_LONG).show();
        }

        db.close();

        //Aumentará en un mes la EditView edtFecha
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date mFecha = null;
        try {
            mFecha = format1.parse(String.valueOf(edtFecha.getText()));
            cal.setTime(mFecha);
            cal.add(Calendar.MONTH, 1);
            String formatted = format1.format(cal.getTime());
            edtFecha.setText(formatted);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Foco a horas facilitando el ingreso de datos
        edtHoras.requestFocus();
        btnIngresarActividad.setBackgroundColor(0x00000000);

    }
        private void incluirlista() {
        //método para incluir la lista de publicadores de la congregacion deseada

        consultarlistaPublicadores(); //llamar método
        ArrayAdapter adapter = new ArrayAdapter
                (getContext(), android.R.layout.simple_spinner_item, listaPublicadores);
        spnPublicador5.setAdapter(adapter);
    }

    private void limpiar() {
        edtHoras.setText("");
        edtRevisitas.setText("");
        edtEstudios.setText("");
        chkPrecursorAuxiliar.setChecked(false);
        edtObservaciones.setText("");

    }

    private void consultarlistaPublicadores() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Publicadores publicador = null;
        publicadoresList = new ArrayList<Publicadores>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE ID_Congregacion = " + nombreCongMap.get(spnCongregacion5.getSelectedItem().toString()) +
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
