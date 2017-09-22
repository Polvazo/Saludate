package com.polvazo.saludate.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.polvazo.saludate.Models.userLogin;
import com.polvazo.saludate.R;
import com.polvazo.saludate.Service.AppointmentService;
import com.polvazo.saludate.Service.ServiceGenerator;
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
    appointmentAdapter adapt;
    Fragment myFragment;
    private SwipeRefreshLayout refresh;
    private AlertDialog alertDialog;
    private Integer numeroCita;
    private Boolean estadoCita;


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
                    adapt = new appointmentAdapter(getActivity(), finalGeneralFilter);
                    if (getActivity() != null) {
                        list.setAdapter(adapt);

                        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                Long idcit = adapt.getItemId(position);
                                numeroCita = idcit.intValue();
                                estadoCita = finalGeneralFilter.get(position).getIs_modifiable();
                                AlertDialogEditar();
                                Log.i("numero cita", String.valueOf(numeroCita));
                                Log.i("Estado", String.valueOf(estadoCita));
                                return false;
                            }
                        });
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<List<General>> call, Throwable t) {
                Log.i("entro", t.getMessage());
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
                    Log.i("pasoO", "aca");
                } else {
                    Log.i("este rrore de mrd", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void AlertDialogEditar() {
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(R.string.f_fda_titleAlert);
        alertDialog.setMessage(getString(R.string.f_fda_messageAlert));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.f_fda_modificarAlert), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                //...

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.f_fda_cancelarAlert), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                Log.i("este estado", String.valueOf(estadoCita));
                if (estadoCita ==false) {
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

}
