package com.polvazo.saludate.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.polvazo.saludate.Adapters.Pager;
import com.polvazo.saludate.Adapters.appointmentAdapter;
import com.polvazo.saludate.Adapters.doctorAdapter;
import com.polvazo.saludate.Constans.Contants;
import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.Models.Speciality;
import com.polvazo.saludate.Models.SpecialityDoctor;
import com.polvazo.saludate.R;
import com.polvazo.saludate.Service.AppointmentService;
import com.polvazo.saludate.Service.ServiceGenerator;
import com.polvazo.saludate.Service.doctorService;
import com.polvazo.saludate.Util.preferencia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private AlertDialog dialog1;
    ArrayList<SpecialityDoctor> doctor;
    ArrayList<Speciality> especialidad;
    doctorAdapter adapt;
    Spinner spinner;
    ArrayList<Speciality> customSpeciality = new ArrayList<>();
    MainActivity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        activity = this;
        spinner = (Spinner) findViewById(R.id.spinner_Doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        //tab id
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);


        //add the tabs
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.a_mai__tabCita));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.a_mai__tabHistorial));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.a_mai__tabPerfil));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        Pager adapter = new Pager(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(adapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCita();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_settings:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.a_log_dialogoSalir_title);
                builder.setMessage(R.string.a_log_dialogoSalir_message);
                builder.setPositiveButton(R.string.a_mai__ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                });
                builder.setNegativeButton(R.string.a_mai__cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                dialog1 = builder.create();
                dialog1.show();

            default:
                return super.onOptionsItemSelected(item);

        }


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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.a_log_dialogoSalir_title)
                .setMessage(R.string.a_log_dialogoSalir_message)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }


    public void getDoctor() {

        spinner = (Spinner) findViewById(R.id.spinner_Doctor);
        doctorService doctorservice = ServiceGenerator.createService(doctorService.class);
        Call<ArrayList<SpecialityDoctor>> call = doctorservice.getSpecialityDoctor();
        call.enqueue(new Callback<ArrayList<SpecialityDoctor>>() {
            @Override
            public void onResponse(Call<ArrayList<SpecialityDoctor>> call, Response<ArrayList<SpecialityDoctor>> response) {
                if (response.isSuccessful()) {
                    Log.i("estado", "entroDoctor");
                    doctor = response.body();
                    for (int i = 0; i < doctor.size(); i++) {
                        Log.i("especialidad", doctor.get(i).getSpeciality().getName());
                    }


                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SpecialityDoctor>> call, Throwable t) {

            }
        });

    }

    public void getEspecialidad() {

        doctorService doctorservice = ServiceGenerator.createService(doctorService.class);
        Call<ArrayList<Speciality>> call = doctorservice.getSpeciality();
        call.enqueue(new Callback<ArrayList<Speciality>>() {
            @Override
            public void onResponse(Call<ArrayList<Speciality>> call, Response<ArrayList<Speciality>> response) {
                if (response.isSuccessful()) {
                    especialidad = response.body();
                    Log.i("estado", "entroDoctor");
                    Speciality esp = new Speciality();
                    for (int i = 0; i < especialidad.size(); i++) {
                        esp.setName(especialidad.get(i).getName());
                        customSpeciality.add(esp);
                    }



                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Speciality>> call, Throwable t) {

            }
        });

    }

    public void newCita() {
        getEspecialidad();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.alertdialog_register_appointment, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setCancelable(false);
        dialog.cancel();
        dialog.show();
    }
}
