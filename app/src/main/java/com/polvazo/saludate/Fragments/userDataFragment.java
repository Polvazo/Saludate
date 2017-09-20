package com.polvazo.saludate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.polvazo.saludate.Constans.Contants;
import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.R;
import com.polvazo.saludate.Service.AppointmentService;
import com.polvazo.saludate.Service.ServiceGenerator;
import com.polvazo.saludate.Util.preferencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suraj on 23/6/17.
 */

public class userDataFragment extends Fragment {
    List<General> general;
    TextView nombre;
    TextView data;
    TextView lugar;
    TextView estadoCivil;
    TextView correo;
    TextView genero;
    TextView dni;
    TextView lugarnac;
    TextView cel;
    ImageView img;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_data, container, false);

        nombre = (TextView) view.findViewById(R.id.txt_data_nombre);
        data = (TextView) view.findViewById(R.id.txt_data_fecha);
        lugar = (TextView) view.findViewById(R.id.txt_data_lugar);
        estadoCivil = (TextView) view.findViewById(R.id.txt_data_estadoCivil);
        correo = (TextView) view.findViewById(R.id.txt_data_email);
        genero = (TextView) view.findViewById(R.id.txt_data_genero);
        dni = (TextView) view.findViewById(R.id.txt_data_dni);
        lugarnac = (TextView) view.findViewById(R.id.txt_departamento);
        cel = (TextView) view.findViewById(R.id.txt_celular);
        img = (ImageView) view.findViewById(R.id.img_data_perfil);
        getData();
        return view;
    }

    public void getData() {
        String idUser = preferencia.obtener(Contants.ID_USUARIO, getActivity());
        Integer id = Integer.parseInt(idUser);
        AppointmentService Appointment = ServiceGenerator.createService(AppointmentService.class);
        Call<List<General>> call = Appointment.getAppointment(id);
        call.enqueue(new Callback<List<General>>() {
            @Override
            public void onResponse(Call<List<General>> call, Response<List<General>> response) {
                if (response.isSuccessful()) {
                    general = response.body();

                    if (general.get(0).getPatient().getPerson().getGender().equals("F")) {
                        img.setImageResource(R.drawable.profile_woman);
                    } else {
                        img.setImageResource(R.drawable.profile_man);
                    }


                    nombre.setText(general.get(0).getPatient().getPerson().getUser().getFirst_name() + " " + general.get(0).getPatient().getPerson().getUser().getLast_name());


                    data.setText(general.get(0).getPatient().getPerson().getBorn_date());


                    lugar.setText(general.get(0).getPatient().getPerson().getHome_address());


                    estadoCivil.setText(general.get(0).getPatient().getCivil_status());


                    correo.setText(general.get(0).getPatient().getPerson().getUser().getEmail());


                    genero.setText(general.get(0).getPatient().getPerson().getGender());


                    dni.setText(general.get(0).getPatient().getPerson().getDni());


                    lugarnac.setText(general.get(0).getPatient().getPerson().getBorn_place().getName_location());


                    cel.setText(general.get(0).getPatient().getPerson().getPhone_number());

                } else {
                }
            }

            @Override
            public void onFailure(Call<List<General>> call, Throwable t) {

            }
        });
    }
}
