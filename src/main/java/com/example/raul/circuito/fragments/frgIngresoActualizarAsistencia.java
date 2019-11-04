package com.example.raul.circuito.fragments;

import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raul.circuito.Adapters.AdapterDatosActualizacionAsistencia;
import com.example.raul.circuito.Clases.ActualizarAsistenciaVo;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgIngresoActualizarAsistencia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoActualizarAsistencia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoActualizarAsistencia extends Fragment {
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
    ArrayList<Congregaciones> congregacionesList;
    ArrayList<String> listaCongregaciones;
    ArrayList<ActualizarAsistenciaVo> listaAsistencia;
    RecyclerView recyclerAsistencia;


    public frgIngresoActualizarAsistencia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoActualizarAsistencia.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoActualizarAsistencia newInstance(String param1, String param2) {
        frgIngresoActualizarAsistencia fragment = new frgIngresoActualizarAsistencia();
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
        View vista = inflater.inflate(R.layout.fragment_frg_ingreso_actualizar_asistencia, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);

        listaAsistencia = new ArrayList<>();

        spnCongregacion = vista.findViewById(R.id.spnCongregacionActualizar);//set al spinner
        consultarlistaCongregaciones(); //llamar al método comenzamos a poblar el spinner Congregaciones
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);

        recyclerAsistencia = vista.findViewById(R.id.RecyclerActualizacionAsistencia);
        recyclerAsistencia.setLayoutManager(new LinearLayoutManager(getContext()));
        final AdapterDatosActualizacionAsistencia adapter = new AdapterDatosActualizacionAsistencia(listaAsistencia);

        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),spnCongregacion.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                llenarLista();
                adapter.notifyDataSetChanged();
                recyclerAsistencia.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(frgIngresoActualizarAsistencia.this.getContext(), R.style.Theme_Dialog_Translucent);
                customDialog.setContentView(R.layout.input_dialog_2);
                EditText ReuEntreSemana = (EditText) customDialog.findViewById(R.id.RESemana);
                EditText ReuFinSemana = (EditText) customDialog.findViewById(R.id.RFSemana);
                Button btnOK = (Button) customDialog.findViewById(R.id.btnOk);
                Button btnCancel = (Button) customDialog.findViewById(R.id.btnCancel);
                ReuEntreSemana.setText("a");
                ReuEntreSemana.setText((listaAsistencia.get(recyclerAsistencia.getChildAdapterPosition(view))).getReuEntreSemana());
                ReuFinSemana.setText((listaAsistencia.get(recyclerAsistencia.getChildAdapterPosition(view))).getReuFinSemana());
                final EditText editText = ReuEntreSemana;
                final EditText editText2 = ReuFinSemana;
                final View view2 = view;
                btnOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View viewOK) {
                        ContentValues values = new ContentValues();
                        values.put(Utilidades.CAMPO_ReuEntreSemana, String.valueOf(editText.getText()));
                        values.put(Utilidades.CAMPO_ReuFinSemana, String.valueOf(editText2.getText()));
                        SQLiteDatabase db = frgIngresoActualizarAsistencia.this.conn.getWritableDatabase();
                        db.update(Utilidades.TABLA_REUNIONES, values, "ID_Mes=?", new String[]{String.valueOf(((ActualizarAsistenciaVo) frgIngresoActualizarAsistencia.this.listaAsistencia.get(frgIngresoActualizarAsistencia.this.recyclerAsistencia.getChildAdapterPosition(view2))).getID_Mes())});
                        db.close();
                        frgIngresoActualizarAsistencia.this.conn.close();
                        ((ActualizarAsistenciaVo) frgIngresoActualizarAsistencia.this.listaAsistencia.get(frgIngresoActualizarAsistencia.this.recyclerAsistencia.getChildAdapterPosition(view2))).setReuEntreSemana(editText.getText().toString());
                        ((ActualizarAsistenciaVo) frgIngresoActualizarAsistencia.this.listaAsistencia.get(frgIngresoActualizarAsistencia.this.recyclerAsistencia.getChildAdapterPosition(view2))).setReuFinSemana(editText2.getText().toString());
                        adapter.notifyItemChanged(frgIngresoActualizarAsistencia.this.recyclerAsistencia.getChildAdapterPosition(view2));
                        customDialog.hide();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        customDialog.hide();
                    }
                });
                customDialog.show();
            }
        });
        return vista;
    }

    private void consultarlistaCongregaciones() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Congregaciones congregacion = null;

        congregacionesList = new ArrayList<Congregaciones>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONGREGACIONES, null);
        while (cursor.moveToNext()) {
            congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1)); //Ingresar nombre de la congregacion
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

    private void llenarLista() {
        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_Mes, Mes, ReuEntreSemana, ReuFinSemana, Nombre " +
                "FROM tbl_REUNIONES, tbl_CONGREGACIONES " +
                "where tbl_CONGREGACIONES.ID_Congregacion = tbl_REUNIONES.ID_Congregacion " +
                "and tbl_CONGREGACIONES.Nombre = '" + spnCongregacion.getSelectedItem().toString() + "'", null);

        listaAsistencia.clear();

        while (cursor.moveToNext()) {
            ActualizarAsistenciaVo asistenciaVo = new ActualizarAsistenciaVo();
            asistenciaVo.setID_Mes(cursor.getString(0));
            asistenciaVo.setMes(cursor.getString(1));
            asistenciaVo.setReuEntreSemana(cursor.getString(2));
            asistenciaVo.setReuFinSemana(cursor.getString(3));

            listaAsistencia.add(asistenciaVo);
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
