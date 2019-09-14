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

import com.example.raul.circuito.Adapters.AdapterDatosEstablecerFechaVisita;
import com.example.raul.circuito.Clases.ActividadVo;
import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgToolsEstablacerFechaVisita.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgToolsEstablacerFechaVisita#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgToolsEstablacerFechaVisita extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    ArrayList<Congregaciones> listaCongregacion;
    ConexionSQLiteHelper conn;


    public frgToolsEstablacerFechaVisita() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgToolsEstablacerFechaVisita.
     */
    // TODO: Rename and change types and number of parameters
    public static frgToolsEstablacerFechaVisita newInstance(String param1, String param2) {
        frgToolsEstablacerFechaVisita fragment = new frgToolsEstablacerFechaVisita();
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
        View vista = inflater.inflate(R.layout.fragment_frg_tools_establacer_fecha_visita, container, false);

        recyclerView = vista.findViewById(R.id.RecyclerDatosCongregaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        conn = new ConexionSQLiteHelper(getContext(), "DATOS", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_CONGREGACIONES", null);

        listaCongregacion = new ArrayList<>();
        listaCongregacion.clear(); //vaciamos si tenemos datos previos

        while (cursor.moveToNext()) {
            Congregaciones congregacionVo = new Congregaciones();
            congregacionVo.setId_congregacion(cursor.getString(0));
            congregacionVo.setnombre(cursor.getString(1));
            congregacionVo.setseccion(cursor.getString(2));
            congregacionVo.setobservaciones(cursor.getString(3));

            listaCongregacion.add(congregacionVo);
        }

        cursor.close();
        db.close();
        conn.close();

        AdapterDatosEstablecerFechaVisita adapter = new AdapterDatosEstablecerFechaVisita(listaCongregacion);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return vista;
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
