package com.polvazo.saludate.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.Models.ScheduleDoctor;
import com.polvazo.saludate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class appointmentAdapter extends BaseAdapter {


    private FragmentActivity activity;
    private LayoutInflater inflater;
    private ArrayList<General> cita;

    public appointmentAdapter(FragmentActivity activity, ArrayList<General> cita) {
        this.activity = activity;
        this.cita = cita;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return cita.size();
    }

    @Override
    public long getItemId(int i){
        return cita.get(i).getId();
    }

    @Override
    public General getItem(int i) {
        return cita.get(i);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View view = inflater.inflate(R.layout.cita_adapter_list, parent, false);

        if(view!=null){
            TextView especialidad = (TextView) view.findViewById(R.id.txt_speciality);
            especialidad.setText(cita.get(position).getSpeciality_doctor().getSpeciality().getName());

            TextView nombreDoctor = (TextView) view.findViewById(R.id.txt_doctor);
            nombreDoctor.setText(cita.get(position).schedule_doctor.getDoctor().getPerson().getUser().getFirst_name() + " " + cita.get(position).schedule_doctor.getDoctor().getPerson().getUser().getLast_name());

            TextView estado = (TextView) view.findViewById(R.id.txt_status);
            estado.setText(cita.get(position).getStatus());

            TextView data = (TextView) view.findViewById(R.id.text_data);
            data.setText(cita.get(position).getSchedule_doctor().getAvailability_date());

            TextView fecha = (TextView) view.findViewById(R.id.text_hora);
            fecha.setText(cita.get(position).getSchedule_doctor().getSchedule().getStart_hour());

            ImageView img = (ImageView) view.findViewById(R.id.img_cita);
            String url = cita.get(position).getSpeciality_doctor().getSpeciality().getImage();
            Picasso.with(activity).load(url).placeholder(R.drawable.noimagen).error(R.drawable.noimagen).into(img);
        }



        return view;
    }
}
