package com.polvazo.saludate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.polvazo.saludate.Adapters.appointmentAdapter;
import com.polvazo.saludate.Constans.Contants;
import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.R;
import com.polvazo.saludate.Service.AppointmentService;
import com.polvazo.saludate.Service.ServiceGenerator;
import com.polvazo.saludate.Util.preferencia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class appointmentHistoryFragment extends Fragment {
    ListView list;
    List<General> general;
    private SwipeRefreshLayout refreshHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_history, container, false);
        list = (ListView) view.findViewById(R.id.listCita);
        refreshHistory = (SwipeRefreshLayout) view.findViewById(R.id.refresHistory);
        refreshHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        refreshHistory.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        appointment();
        return view;
    }

    private void refreshContent() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                appointment();

                refreshHistory.setRefreshing(false);
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
                        if (!general.get(i).getStatus().equals("Por Atender")) {
                            finalGeneralFilter.add(general.get(i));
                        }

                    }

                    Log.i("entro", "fragments");
                    appointmentAdapter adapt = new appointmentAdapter(getActivity(), finalGeneralFilter);
                    if (getActivity()!=null){
                        list.setAdapter(adapt);
                    }

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
}
