package com.example.raul.circuito.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgIngresoCongregaciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgIngresoCongregaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgIngresoCongregaciones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText campoNuevaCongregacion, campoSeccion, campoObservaciones;
    Button btnIngresar;

    public frgIngresoCongregaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgIngresoCongregaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static frgIngresoCongregaciones newInstance(String param1, String param2) {
        frgIngresoCongregaciones fragment = new frgIngresoCongregaciones();
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
        View vista = inflater.inflate(R.layout.fragment_frg_ingreso_congregaciones, container, false);

        btnIngresar = vista.findViewById(R.id.btnIngresar);
        campoNuevaCongregacion = vista.findViewById(R.id.edtNombreCongregacion);
        campoSeccion = vista.findViewById(R.id.edtSeccion);
        campoObservaciones = vista.findViewById(R.id.edtObservaciones);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((campoNuevaCongregacion.getText().toString().equals("")) || (campoSeccion.getText().toString().equals(""))) {
                    Toast.makeText(getContext(),"Rellene los campos arriba indicados",Toast.LENGTH_LONG).show();
                } else {
                    ejecutar();
                }

            }
        });
        return vista;
    }

    private void  ejecutar() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"ICA-04", null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        try {
            ContentValues values=new ContentValues();
            values.put("Nombre", campoNuevaCongregacion.getText().toString());
            values.put("Seccion", campoSeccion.getText().toString());
            values.put("Observaciones", campoObservaciones.getText().toString());
            db.insert("tbl_CONGREGACIONES","Observaciones", values);
            Toast.makeText(getContext(),"Se registró en la base de datos", Toast.LENGTH_LONG).show();
            limpiar();
        }catch (Exception e) {
            Toast.makeText(getContext(),"No se registró en la base de datos", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void limpiar() {
        campoNuevaCongregacion.setText("");
        campoSeccion.setText("");
        campoObservaciones.setText("");
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
