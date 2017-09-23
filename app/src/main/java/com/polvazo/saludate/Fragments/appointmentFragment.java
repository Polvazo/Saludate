package com.polvazo.saludate.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.polvazo.saludate.Activity.MainActivity;
import com.polvazo.saludate.Activity.login;
import com.polvazo.saludate.Adapters.appointmentAdapter;
import com.polvazo.saludate.Adapters.doctorAdapter;
import com.polvazo.saludate.Adapters.especialidadAdapter;
import com.polvazo.saludate.Adapters.horarioAdapter;
import com.polvazo.saludate.Constans.Contants;
import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.Models.ScheduleDoctor;
import com.polvazo.saludate.Models.Speciality;
import com.polvazo.saludate.Models.SpecialityDoctor;
import com.polvazo.saludate.Models.appointmentProcess;
import com.polvazo.saludate.Models.userLogin;
import com.polvazo.saludate.R;
import com.polvazo.saludate.Service.AppointmentService;
import com.polvazo.saludate.Service.ServiceGenerator;
import com.polvazo.saludate.Service.doctorService;
import com.polvazo.saludate.Service.userService;
import com.polvazo.saludate.Util.preferencia;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class appointmentFragment extends Fragment {

    ListView list;
    List<General> general;
    appointmentAdapter adapter;
    Fragment myFragment;
    private SwipeRefreshLayout refresh;
    private AlertDialog alertDialog;
    private Integer numeroCita;
    private Boolean estadoCita;
    private MainActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private android.support.v7.app.AlertDialog dialog1;
    ArrayList<SpecialityDoctor> doctor;
    ArrayList<ScheduleDoctor> horario;
    ArrayList<Speciality> especialidad;
    especialidadAdapter adapt;
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
    private android.support.v7.app.AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        setRetainInstance(true);

        list = (ListView) view.findViewById(R.id.listCita);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        appointment();
        return view;
    }

    private void refreshContent() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                appointment();

                refresh.setRefreshing(false);
            }
        }, 1500);
    }

    public void appointment() {

        Log.i("entro", "fragments");

        String idUser = preferencia.obtener(Contants.ID_USUARIO, getActivity());
        Integer id = Integer.parseInt(idUser);
        AppointmentService Appointment = ServiceGenerator.createService(AppointmentService.class);
        Call<List<General>> call = Appointment.getAppointment(id);
        final ArrayList<General> finalGeneralFilter = new ArrayList<>();
        call.enqueue(new Callback<List<General>>() {
            @Override
            public void onResponse(Call<List<General>> call, Response<List<General>> response) {
                if (response.isSuccessful()) {

                    general = response.body();
                    for (int i = 0; i < general.size(); i++) {
                        if (general.get(i).getStatus().equals("Por Atender")) {
                            finalGeneralFilter.add(general.get(i));
                        }

                    }

                    Log.i("entro", "fragments");

                    if (getActivity() != null) {
                        adapter = new appointmentAdapter(getActivity(), finalGeneralFilter);
                        list.setAdapter(adapter);
                    }
                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            Long idcit = adapter.getItemId(position);
                            numeroCita = idcit.intValue();
                            estadoCita = finalGeneralFilter.get(position).getIs_modifiable();
                            AlertDialogEditar();
                            Log.i("numero cita", String.valueOf(numeroCita));
                            Log.i("Estado", String.valueOf(estadoCita));
                            return false;
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<General>> call, Throwable t) {
                Log.i("entro", t.getMessage());
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void modificarNuevaCita() {

        String idUser = preferencia.obtener(Contants.ID_USUARIO, getActivity());
        Integer id = Integer.parseInt(idUser);
        AppointmentService Appointment = ServiceGenerator.createService(AppointmentService.class);
        appointmentProcess general = new appointmentProcess(fechaPost, doctorPost, id, descripcionPost, anotatioPost, Contants.ESTADO_CITA_ATENDER);
        Call<ResponseBody> call = Appointment.cambiarCita(numeroCita, general);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("pasoO", "por esta mierda paso csm");
                } else {
                    Log.i("este rrore de mrd PUT", String.valueOf(response.code()));
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CancelarCita() {


        AppointmentService Appointment = ServiceGenerator.createService(AppointmentService.class);
        General general = new General(Contants.ESTADO_CITA_CANCELADO);
        Call<ResponseBody> call = Appointment.modificarCita(numeroCita, general);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("pasoO", "por esta mierda paso");
                } else {
                    Log.i("este rrore de mrd", String.valueOf(response.code()));
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AlertDialogEditar() {
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(R.string.f_fda_titleAlert);
        alertDialog.setMessage(getString(R.string.f_fda_messageAlert));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.f_fda_modificarAlert), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                if (estadoCita == false) {

                    Toast.makeText(getActivity(), "No se puede cancelar su cita, su cita es muy pronto...!!", Toast.LENGTH_SHORT).show();
                } else {
                    newCita();
                }

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.f_fda_cancelarAlert), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                Log.i("este estado", String.valueOf(estadoCita));
                if (estadoCita == false) {
                    Toast.makeText(getActivity(), "No se puede cancelar su cita, su cita es muy pronto...!!", Toast.LENGTH_SHORT).show();
                } else {
                    CancelarCita();
                }


            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.f_fda_salirAlert), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();

                //...

            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);
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

                    doctoradapt = new doctorAdapter(getActivity(), finallist);
                    spinner2.setAdapter(doctoradapt);
                    spinner2.setSelection(doctoradapt.NO_SELECTION, false);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            horarioFiltrado = spinner2.getSelectedItem().toString();
                            doctorPost = doctoradapt.getItem(position).getId();
                            getFecha(horarioFiltrado);
                            Log.i("doctor", horarioFiltrado);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SpecialityDoctor>> call, Throwable t) {
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
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


                    adapt = new especialidadAdapter(getActivity(), especialidad);


                    spinner.setAdapter(adapt);
                    spinner.setSelection(adapt.NO_SELECTION, false);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            especialidadFiltrada = spinner.getSelectedItem().toString();
                            especialidadPost = adapt.getItem(position).getId();
                            getDoctor(especialidadFiltrada);
                            Log.i("espcialidad", especialidadFiltrada);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Speciality>> call, Throwable t) {
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
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
                    horarioadapter = new horarioAdapter(getActivity(), horarioFinal);

                    spinner3.setSelection(adapt.NO_SELECTION, true);
                    spinner3.setAdapter(horarioadapter);

                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            fechaPost = horarioadapter.getItem(position).getId();
                            String fecha2 = spinner3.getSelectedItem().toString();
                            Log.i("fechaMierda", fecha2);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleDoctor>> call, Throwable t) {
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void newCita() {

        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.alertdialog_register_appointment, null);
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
        descripcionPost = descripcion.getText().toString().trim();
        anotatioPost = anotation.getText().toString().trim();
        nuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (anotatioPost.isEmpty() && descripcionPost.isEmpty() && fechaPost == null && doctorPost == null) {
                    Toast.makeText(getActivity(), "Completar todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                modificarNuevaCita();
                dialog.dismiss();}
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
}
