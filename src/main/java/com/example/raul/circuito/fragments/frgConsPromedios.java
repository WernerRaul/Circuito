package com.example.raul.circuito.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.GlobalClass;
import com.example.raul.circuito.R;
import com.example.raul.circuito.Utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgConsPromedios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgConsPromedios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgConsPromedios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ConexionSQLiteHelper conn;
    TextView lblHoras;
    TextView lblRevisitas;
    TextView lblEstudiosBiblicos;
    TextView lblPublicadores;
    TextView lblPrecRegulares;
    TextView lblPrecAuxiliar;
    TextView lblReuEntreSemana;
    TextView lblReuFinSemana;
    TextView lblTerritorios;

    public frgConsPromedios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgConsPromedios.
     */
    // TODO: Rename and change types and number of parameters
    public static frgConsPromedios newInstance(String param1, String param2) {
        frgConsPromedios fragment = new frgConsPromedios();
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
        View vista = inflater.inflate(R.layout.fragment_frg_cons_promedios, container, false);
        lblHoras = vista.findViewById(R.id.lblHoras);
        lblRevisitas = vista.findViewById(R.id.lblRevisitas);
        lblEstudiosBiblicos = vista.findViewById(R.id.lblEstudios);
        lblPublicadores = vista.findViewById(R.id.lblPublicadores);
        lblPrecRegulares = vista.findViewById(R.id.lblPrecRegulares);
        lblPrecAuxiliar = vista.findViewById(R.id.lblPrecAuxiliares);
        lblReuEntreSemana = vista.findViewById(R.id.lblReuEntreSemana);
        lblReuFinSemana = vista.findViewById(R.id.lblReuFinSemana);
        lblTerritorios = vista.findViewById(R.id.lblTerritorios);

        conn = new ConexionSQLiteHelper(getContext(), "ICA-04", null, 1);

        GlobalClass globalClass = (GlobalClass) getContext().getApplicationContext();


        SQLiteDatabase db = conn.getReadableDatabase();
        String [] argumentos = {globalClass.getFechaA(),globalClass.getFechaB()};

        //Horas,Revisitas,Estudios
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_PUBLICADORES.PrecRegular = 'false') INNER JOIN tbl_ACTIVIDAD ON " +
                "(tbl_ACTIVIDAD.ID_Publicador = tbl_PUBLICADORES.ID_Publicador " +
                "AND tbl_ACTIVIDAD.PAuxiliar = 'false')");

        String[] sqlSelect = {
                "round(sum(tbl_ACTIVIDAD.Horas * 1.0) / count(tbl_PUBLICADORES.ID_Publicador * 1.0),1)",
                "round(sum(tbl_ACTIVIDAD.Revisitas * 1.0) / count(tbl_PUBLICADORES.ID_Publicador * 1.0),1)",
                "round(sum(tbl_ACTIVIDAD.Estudios * 1.0) / count(tbl_PUBLICADORES.ID_Publicador * 1.0),1)"};
        Cursor c = builder.query(db, sqlSelect, "tbl_ACTIVIDAD.AñoMes between ? and ?", argumentos, "tbl_CONGREGACIONES.Nombre",
                "tbl_CONGREGACIONES.ID_Congregacion = '" + String.valueOf(globalClass.getIdCongregacion()) + "'", null);
        c.moveToNext();

        lblHoras.setText(c.getString(0) + "   Horas por Publicador");
        lblRevisitas.setText(c.getString(1) + "   Revisitas por Publicador");
        lblEstudiosBiblicos.setText(c.getString(2) + "   Estudios por Publicador");
        c.close();

        //Publicadores
        SQLiteQueryBuilder builder2 = new SQLiteQueryBuilder();
        builder2.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON" +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.ID_Congregacion = '" + String.valueOf(globalClass.getIdCongregacion()) + "' " +
                "AND tbl_PUBLICADORES.PrecRegular = 'false')");
        String[] sqlSelect2 = {
                "count(tbl_PUBLICADORES.Nombre)"};
        Cursor d = builder2.query(db, sqlSelect2, null, null, null, null, null);
        d.moveToNext();

        lblPublicadores.setText(d.getString(0) + " Publicadores");
        d.close();

        //Precursores Regulares
        SQLiteQueryBuilder builder3 = new SQLiteQueryBuilder();
        builder3.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON" +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.ID_Congregacion = '" + String.valueOf(globalClass.getIdCongregacion()) + "' " +
                "AND tbl_PUBLICADORES.PrecRegular = 'true')");
        String[] sqlSelect3 = {
                "count(tbl_PUBLICADORES.Nombre)"};
        Cursor e = builder3.query(db, sqlSelect3, null, null, null, null, null);
        e.moveToNext();

        lblPrecRegulares.setText(e.getString(0) + " Precursores Regulares");
        e.close();

        //Precursores Auxiliares
        SQLiteQueryBuilder builder4 = new SQLiteQueryBuilder();
        builder4.setTables("tbl_CONGREGACIONES INNER JOIN tbl_PUBLICADORES ON" +
                "(tbl_CONGREGACIONES.ID_Congregacion = tbl_PUBLICADORES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.ID_Congregacion = '" + String.valueOf(globalClass.getIdCongregacion()) + "') INNER JOIN " +
                "tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador AND " +
                "tbl_ACTIVIDAD.PAuxiliar = 'true')");
        String[] sqlSelect4 = {
                "tbl_PUBLICADORES.Nombre"
        };
        Cursor f = builder4.query(db, sqlSelect4, "tbl_ACTIVIDAD.AñoMes between ? and ?", argumentos, "tbl_PUBLICADORES.Nombre", null, null);
        f.moveToNext();

        lblPrecAuxiliar.setText(f.getCount() + " Precursores Auxiliares");
        f.close();

        //Asistencia
        SQLiteQueryBuilder builder5 = new SQLiteQueryBuilder();
        String [] argumentos1 = {String.valueOf(globalClass.getIdCongregacion()),globalClass.getFechaA(),globalClass.getFechaB()};
        builder5.setTables("tbl_REUNIONES INNER JOIN tbl_CONGREGACIONES ON" +
                "(tbl_REUNIONES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion)");
        String[] sqlSelect5 = {"round(AVG(tbl_REUNIONES.ReuEntreSemana),1)", "round(AVG(tbl_REUNIONES.ReuFinSemana),1)"};
        Cursor g = builder5.query(db, sqlSelect5, "tbl_CONGREGACIONES.ID_Congregacion = ? and tbl_REUNIONES.Mes between ? and ?", argumentos1, "tbl_CONGREGACIONES.Nombre", null, null);
        g.moveToNext();

        lblReuEntreSemana.setText(g.getString(0) + " Reunión entre semana");
        lblReuFinSemana.setText(g.getString(1) + " Reunión fin de semana");
        g.close();

        //Territorios
        SQLiteDatabase builder6 = conn.getReadableDatabase();
        Cursor h = builder6.rawQuery("SELECT Territorios FROM " + Utilidades.TABLA_CONGREGACIONES +
                " WHERE "+ Utilidades.CAMPO_ID_CONGREGACION + " = '" + String.valueOf(globalClass.getIdCongregacion()) + "'" , null);
        h.moveToNext();
        lblTerritorios.setText(h.getString(0) + " Territorios");
        h.close();

        db.close();
        conn.close();

        // Inflate the layout for this fragment
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
