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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
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
 * {@link frgIngresoAsistenciaReuniones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoAsistenciaReuniones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoAsistenciaReuniones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spnCongregacion;
    Button btnIngresar;
    EditText edtFecha;
    EditText edtReunionEntreSemana;
    EditText edtReunionFinSemana;
    EditText edtObservaciones;
    TextView lblUltimoRegistro;

    ConexionSQLiteHelper conn;
    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    Map<String,Integer> nombreMap;

    public frgIngresoAsistenciaReuniones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoAsistenciaReuniones.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoAsistenciaReuniones newInstance(String param1, String param2) {
        frgIngresoAsistenciaReuniones fragment = new frgIngresoAsistenciaReuniones();
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
        View vista = inflater.inflate(R.layout.fragment_frg_ingreso_asistencia_reuniones, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);

        edtFecha = vista.findViewById(R.id.edtFecha);
        edtReunionEntreSemana = vista.findViewById(R.id.edtReunionEntreSemana);
        edtReunionFinSemana = vista.findViewById(R.id.edtReunionFinSemana);
        edtObservaciones = vista.findViewById(R.id.edtObservaciones);
        lblUltimoRegistro = vista.findViewById(R.id.lblUltimoRegistro);
        nombreMap = new HashMap<String, Integer>();

        //Llenar datos de congregaciones en spinner
        spnCongregacion = vista.findViewById(R.id.spnCongregacion);
        consultarlistaCongregaciones();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);
        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnIngresar = vista.findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((spnCongregacion.getSelectedItemId() == 0) ||
                        edtFecha.getText().toString().equals("") ||
                        edtReunionEntreSemana.getText().toString().equals("") ||
                        edtReunionFinSemana.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Relene los campos arriba indicados",Toast.LENGTH_LONG).show();
                } else {
                    ejecutar();
                }

            }
        });

        btnIngresar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnIngresar.setBackgroundColor(0xFFE51A4C);
            }
        });
        return vista;
    }

    private void ejecutar(){
        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        SQLiteDatabase db=conn.getWritableDatabase();
        String texto=null;

        try {
            ContentValues values=new ContentValues();
            values.put("Mes", edtFecha.getText().toString());
            values.put("ReuEntreSemana", edtReunionEntreSemana.getText().toString());
            values.put("ReuFinSemana", edtReunionFinSemana.getText().toString());
            values.put("Observaciones", edtObservaciones.getText().toString());
            values.put("ID_Congregacion",nombreMap.get(spnCongregacion.getSelectedItem().toString()));

            db.insert("tbl_REUNIONES","Observaciones", values);
            texto = "Fecha: " +edtFecha.getText().toString()+", RES: "+edtReunionEntreSemana.getText().toString()+
                    ", RFS: "+edtReunionFinSemana.getText().toString()+", registrado en la Base de Datos";
            Toast.makeText(getContext(),texto, Toast.LENGTH_LONG).show();
            limpiar();

        }catch(Exception e){
            Toast.makeText(getContext(),"No se registró en la base de datos. Error: "+e, Toast.LENGTH_LONG).show();
        }

        db.close();
        lblUltimoRegistro.setText(texto);

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

        edtReunionEntreSemana.requestFocus();
        btnIngresar.setBackgroundColor(0x00000000);

    }

    private void limpiar() {
        edtReunionEntreSemana.setText("");
        edtReunionFinSemana.setText("");
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
