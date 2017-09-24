package com.saludate.saludate.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.saludate.saludate.Adapters.Pager;

import com.saludate.saludate.Adapters.doctorAdapter;
import com.saludate.saludate.Adapters.especialidadAdapter;
import com.saludate.saludate.Adapters.horarioAdapter;
import com.saludate.saludate.Constans.Contants;

import com.saludate.saludate.Models.ScheduleDoctor;
import com.saludate.saludate.Models.Speciality;
import com.saludate.saludate.Models.SpecialityDoctor;

import com.polvazo.saludate.R;
import com.saludate.saludate.Models.userNotification;
import com.saludate.saludate.Service.AppointmentService;
import com.saludate.saludate.Service.ServiceGenerator;
import com.saludate.saludate.Service.doctorService;
import com.saludate.saludate.Service.notificationService;
import com.saludate.saludate.Util.preferencia;


import java.util.ArrayList;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ArrayList<userNotification> userNotifications;
    private Thread t = null;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private AlertDialog dialog1;
    ArrayList<SpecialityDoctor> doctor;
    ArrayList<ScheduleDoctor> horario;
    ArrayList<Speciality> especialidad;
    especialidadAdapter adapt;
    private Activity context = this;
    doctorAdapter doctoradapt;
    horarioAdapter horarioadapter;
    Spinner spinner;
    Spinner spinner3;
    String especialidadFiltrada;
    String horarioFiltrado;
    Spinner spinner2;
    MainActivity activity = null;
    private Integer especialidadPost;
    private Integer doctorPost;
    private Integer fechaPost;
    private EditText descripcion;
    private EditText anotation;
    private String descripcionPost;
    private String anotatioPost;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        Notification();

        activity = this;

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
        if (getApplicationContext() != null) {
            mViewPager.setAdapter(adapter);
        }


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


    public void getDoctor(final String especialidadFiltrada) {


        final doctorService doctorservice = ServiceGenerator.createService(doctorService.class);
        Call<ArrayList<SpecialityDoctor>> call = doctorservice.getSpecialityDoctor();
        final ArrayList<SpecialityDoctor> finallist = new ArrayList<>();
        call.enqueue(new Callback<ArrayList<SpecialityDoctor>>() {
            @Override
            public void onResponse(Call<ArrayList<SpecialityDoctor>> call, Response<ArrayList<SpecialityDoctor>> response) {
                if (response.isSuccessful()) {
                    Log.i("estado", "entroDoctor Especilaidad");
                    doctor = response.body();
                    for (int i = 0; i < doctor.size(); i++) {
                        if (doctor.get(i).getSpeciality().getName().equals(especialidadFiltrada)) {
                            finallist.add(doctor.get(i));

                        }
                    }

                    doctoradapt = new doctorAdapter(context, finallist);
                    spinner2.setAdapter(doctoradapt);
                    spinner2.setSelection(doctoradapt.NO_SELECTION, false);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            horarioFiltrado = spinner2.getSelectedItem().toString();
                            Long idcit = doctoradapt.getItemId(position);
                            doctorPost = idcit.intValue();
                            getFecha(horarioFiltrado);
                            Log.i("doctor", horarioFiltrado);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SpecialityDoctor>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
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

                    adapt = new especialidadAdapter(context, especialidad);
                    spinner.setAdapter(adapt);
                    spinner.setSelection(adapt.NO_SELECTION, false);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            especialidadFiltrada = spinner.getSelectedItem().toString();
                            Long idcit = adapt.getItemId(position);
                            especialidadPost = idcit.intValue();

                            getDoctor(especialidadFiltrada);
                            Log.i("espcialidad", especialidadFiltrada);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Speciality>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getFecha(final String doctore) {

        Log.i("entro", "horario");

        doctorService doctorservice = ServiceGenerator.createService(doctorService.class);
        Call<ArrayList<ScheduleDoctor>> call = doctorservice.getHorario();
        final ArrayList<ScheduleDoctor> horarioFinal = new ArrayList<>();
        call.enqueue(new Callback<ArrayList<ScheduleDoctor>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleDoctor>> call, Response<ArrayList<ScheduleDoctor>> response) {
                if (response.isSuccessful()) {
                    horario = response.body();
                    Log.i("entro", "horario respondio");
                    for (int i = 0; i < horario.size(); i++) {
                        if ((horario.get(i).getDoctor().getPerson().getUser().getFirst_name() + " " + horario.get(i).getDoctor().getPerson().getUser().getLast_name()).equals(doctore)) {
                            horarioFinal.add(horario.get(i));
                        }
                    }
                    horarioadapter = new horarioAdapter(context, horarioFinal);
                    spinner3.setSelection(adapt.NO_SELECTION, false);
                    spinner3.setAdapter(horarioadapter);

                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Long idcit = horarioadapter.getItemId(position);
                            fechaPost = idcit.intValue();

                            String fecha2 = spinner3.getSelectedItem().toString();
                            Log.i("fechaMierda", fecha2);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleDoctor>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void newCita() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.alertdialog_register_appointment, null);
        mBuilder.setView(mView);
        Button nuevaCita = (Button) mView.findViewById(R.id.btn_new_cita);
        Button cancelar = (Button) mView.findViewById(R.id.btn_salir);
        anotation = (EditText) mView.findViewById(R.id.et_anotation);
        descripcion = (EditText) mView.findViewById(R.id.et_descripcion);
        spinner = (Spinner) mView.findViewById(R.id.spinner_Especialidad);
        dialog = mBuilder.create();
        spinner2 = (Spinner) mView.findViewById(R.id.spinner_Doctor);
        spinner3 = (Spinner) mView.findViewById(R.id.spinner_Horario);
        getEspecialidad();

        nuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                anotatioPost = anotation.getText().toString().trim();
                descripcionPost = descripcion.getText().toString().trim();
                if (anotatioPost.isEmpty() && descripcionPost.isEmpty() && fechaPost == null && doctorPost == null) {
                    Toast.makeText(getApplication().getApplicationContext(), "Completar todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    ProgramarCita();
                    dialog.dismiss();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.cancel();
        dialog.show();
    }

    public void ProgramarCita() {


        String idUser = preferencia.obtener(Contants.ID_USUARIO, getApplicationContext());
        Integer id = Integer.parseInt(idUser);
        Log.i("idusuario", String.valueOf(id));
        AppointmentService nuevacita = ServiceGenerator.createService(AppointmentService.class);
        Call<ResponseBody> call = nuevacita.crearNuevaCita(fechaPost, doctorPost, id, anotatioPost, descripcionPost, Contants.ESTADO_CITA_ATENDER);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("que error es ", String.valueOf(response.isSuccessful()));
                Log.i("cita datos", call.toString());
                if (response.isSuccessful()) {
                    Log.i("se creo cita", "se creo");
                    Toast.makeText(getApplication().getApplicationContext(), "Nueva cita creada", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Log.i("Error de conexion : crear cita", String.valueOf(response.code()));
                    Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("se creo cita", "no conexion");
                Toast.makeText(getApplication().getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (t != null) {
            if (t.isAlive()) {
                t.interrupt();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Thread() {
            public void run() {
                try {
                    sleep(5 * 120 * 1000);
                    Contants.CERRAR_SESION = true;
                    Log.i("CerraSerion", String.valueOf(Contants.CERRAR_SESION));

                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);


                } catch (InterruptedException e) {
                    return;
                }
            }
        }.start();
    }

    public void Notification() {
        String idUser = preferencia.obtener(Contants.ID_USUARIO, getApplicationContext());
        Integer id = Integer.parseInt(idUser);
        notificationService notification = ServiceGenerator.createService(notificationService.class);
        Call<ArrayList<userNotification>> call = notification.getNotification(id);
        call.enqueue(new Callback<ArrayList<userNotification>>() {
            @Override
            public void onResponse(Call<ArrayList<userNotification>> call, Response<ArrayList<userNotification>> response) {
                if (response.isSuccessful()) {
                    Log.i("entro por esta mierda", "si entro");
                    userNotifications = response.body();
                    if (userNotifications.isEmpty()) {
                        Log.i("eso es lo que pasa", "se creara");
                        postNotification();
                    } else {
                        Log.i("eso es lo que pasa", "ya estaba creado esto");
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<userNotification>> call, Throwable t) {

            }
        });
    }

    public void postNotification() {

        String idUser = preferencia.obtener(Contants.ID_PERSON, getApplicationContext());
        Integer id = Integer.parseInt(idUser);
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        String idOneSignal = status.getSubscriptionStatus().getUserId();
        Log.i("errore", idOneSignal);
        Log.i("errore", String.valueOf(id));
        notificationService notification = ServiceGenerator.createService(notificationService.class);
        Call<ResponseBody> call = notification.postNotification(id, idOneSignal);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("enetro al post", "estoy seguro");
                } else {
                    Log.i("putoErrorPost", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
