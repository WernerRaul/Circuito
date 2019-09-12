package com.example.raul.circuito;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.raul.circuito.Clases.ConexionSQLiteHelper;
import com.example.raul.circuito.Clases.GlobalClass;
import com.example.raul.circuito.fragments.frgConsAuxiliares;
import com.example.raul.circuito.fragments.frgConsBajaActividad;
import com.example.raul.circuito.fragments.frgConsIrregulares;
import com.example.raul.circuito.fragments.frgConsNoEstudios;
import com.example.raul.circuito.fragments.frgConsNoRevisitas;
import com.example.raul.circuito.fragments.frgConsPromedios;
import com.example.raul.circuito.fragments.frgConsPublicadores;
import com.example.raul.circuito.fragments.frgConsRegulares;
import com.example.raul.circuito.fragments.frgConsVarones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TabbedConsActivity extends AppCompatActivity implements
    frgConsPublicadores.OnFragmentInteractionListener,
    frgConsVarones.OnFragmentInteractionListener,
    frgConsPromedios.OnFragmentInteractionListener,
    frgConsAuxiliares.OnFragmentInteractionListener,
    frgConsRegulares.OnFragmentInteractionListener,
    frgConsBajaActividad.OnFragmentInteractionListener,
    frgConsIrregulares.OnFragmentInteractionListener,
    frgConsNoRevisitas.OnFragmentInteractionListener,
    frgConsNoEstudios.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_cons);

        /*Abrimos la base de datos para saber que congregación ha sido cliqueda
        * esa información será presentada en el toolbar mediante la variable mCong*/
        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONGREGACIONES
                +" WHERE "+ Utilidades.CAMPO_ID_CONGREGACION + " = " + globalClass.getIdCongregacion(),null);
        cursor.moveToNext();
        String mCong = cursor.getString(1); //obtuvimos la información
        cursor.close();
        db.close();
        conn.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(mCong); //la información de la base de datos es presentada en el toolbar

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_cons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        //Selección del ID de congregación a partir del nombre y fecha de última visita
        GlobalClass globalClass = (GlobalClass) getApplicationContext();

        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONGREGACIONES
                +" WHERE "+ Utilidades.CAMPO_ID_CONGREGACION
                + " = '" +globalClass.getIdCongregacion()
                +"'",null);
        cursor.moveToNext();
        String fecha3=cursor.getString(3); //fecha de última visita
        cursor.close();
        db.close();
        conn.close();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = null;
        try {
            fechaDate = format.parse(fecha3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(fechaDate);
        calendar2.add(Calendar.MONTH,-5);
        String fecha2 = String.format("%1$tY-%1$tm-%1$td",calendar2.getTime());

        Calendar calendar2a = Calendar.getInstance();
        calendar2a.setTime(fechaDate);
        calendar2a.add(Calendar.MONTH,-6);
        String fecha2a = String.format("%1$tY-%1$tm-%1$td",calendar2a.getTime());


        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(fechaDate);
        calendar1.add(Calendar.MONTH,-11);
        String fecha1 = String.format("%1$tY-%1$tm-%1$td",calendar1.getTime());

        if (id == R.id.ultimoSemestre) {
            //Aqui se pondra la última fecha de visita mas la fecha de la resta entre 6, fecha2(fecha de visita -6) y fecha3(fecha de visita)
            Toast.makeText(getApplicationContext(),"Selección: Último Semestre del "+fecha2+" al "+fecha3, Toast.LENGTH_SHORT).show();
            globalClass.setFechaA(fecha2);
            globalClass.setFechaB(fecha3);
            Intent miIntent = new Intent(getApplicationContext(),TabbedConsActivity.class);
            startActivity(miIntent);
            return true;
        } else if (id == R.id.penultimoSemestre){
            //Aqui se pondra la fecha de visita - 6 mas la fecha de visita -12, fecha1(fecha de visita -12) y fecha2(fecha de visita - 6)
            Toast.makeText(getApplicationContext(),"Selección: Penúltimo Semestre del " +fecha1+ " al "+fecha2a, Toast.LENGTH_LONG).show();
            globalClass.setFechaA(fecha1);
            globalClass.setFechaB(fecha2a);
            Intent miIntent = new Intent(getApplicationContext(),TabbedConsActivity.class);
            startActivity(miIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment=null;
            switch (sectionNumber){
                case 1:fragment=new frgConsPublicadores();
                    break;
                case 2:fragment=new frgConsVarones();
                    break;
                case 3:fragment=new frgConsPromedios();
                    break;
                case 4:fragment=new frgConsAuxiliares();
                    break;
                case 5:fragment=new frgConsRegulares();
                    break;
                case 6:fragment=new frgConsBajaActividad();
                    break;
                case 7:fragment=new frgConsIrregulares();
                    break;
                case 8:fragment=new frgConsNoRevisitas();
                    break;
                case 9:fragment=new frgConsNoEstudios();
                    break;
            }
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_cons, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 9 total pages.
            return 9;
        }
    }
}
