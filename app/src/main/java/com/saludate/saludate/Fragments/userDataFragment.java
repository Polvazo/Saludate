package com.saludate.saludate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saludate.saludate.Constans.Contants;
import com.saludate.saludate.Models.General;
import com.polvazo.saludate.R;
import com.saludate.saludate.Models.MedicalRecord;
import com.saludate.saludate.Service.AppointmentService;
import com.saludate.saludate.Service.ServiceGenerator;
import com.saludate.saludate.Util.preferencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suraj on 23/6/17.
 */

public class userDataFragment extends Fragment {

    MedicalRecord medical;
    private TextView nombre;
    private TextView data;
    private TextView lugar;
    private TextView estadoCivil;
    private TextView correo;
    private TextView genero;
    private TextView dni;
    private TextView lugarnac;
    private TextView cel;
    private TextView altura;
    private TextView peso;
    private TextView sangre;
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
        altura = (TextView) view.findViewById(R.id.txt_altura);
        peso = (TextView) view.findViewById(R.id.txt_peso);
        sangre = (TextView) view.findViewById(R.id.txt_tipo_sangre);


        getData();


        return view;
    }

    public void getData() {
        String idUser = preferencia.obtener(Contants.ID_USUARIO, getActivity());
        Integer id = Integer.parseInt(idUser);
        Log.i("usuario", String.valueOf(id));
        AppointmentService Appointment = ServiceGenerator.createService(AppointmentService.class);
        Call<MedicalRecord> call = Appointment.getMedicalRecord(id);
        call.enqueue(new Callback<MedicalRecord>() {
            @Override
            public void onResponse(Call<MedicalRecord> call, Response<MedicalRecord> response) {
                if (response.isSuccessful()) {
                    medical = response.body();

                    if (medical != null) {

                        if (medical.getPatient().getPerson().getGender().equals("F")) {
                            img.setImageResource(R.drawable.profile_woman);
                        } else {
                            img.setImageResource(R.drawable.profile_man);
                        }


                        nombre.setText(medical.getPatient().getPerson().getUser().getFirst_name() + " " + medical.getPatient().getPerson().getUser().getLast_name());
                        data.setText(medical.getPatient().getPerson().getBorn_date());
                        lugar.setText(medical.getPatient().getPerson().getHome_address());
                        estadoCivil.setText(medical.getPatient().getCivil_status());
                        correo.setText(medical.getPatient().getPerson().getUser().getEmail());
                        genero.setText(medical.getPatient().getPerson().getGender());
                        dni.setText(medical.getPatient().getPerson().getDni());
                        lugarnac.setText(medical.getPatient().getPerson().getBorn_place().getName_location());
                        cel.setText(medical.getPatient().getPerson().getPhone_number());
                        float myfloatvariable=medical.getHeight();
                        String alturaget=Float.toString(myfloatvariable);
                        altura.setText(alturaget);
                        float myfloatvariable1=medical.getWeight();
                        String pesoget=Float.toString(myfloatvariable1);
                        peso.setText(pesoget);
                        sangre.setText(medical.getBlood_type());

                    }
                } else {
                    Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MedicalRecord> call, Throwable t) {
                Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
