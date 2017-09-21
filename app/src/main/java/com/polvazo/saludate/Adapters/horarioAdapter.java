package com.polvazo.saludate.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polvazo.saludate.Models.ScheduleDoctor;
import com.polvazo.saludate.Models.Speciality;

import com.polvazo.saludate.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 21/09/2017.
 */

public class horarioAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<ScheduleDoctor> horario;

    public horarioAdapter(Activity activity, ArrayList<ScheduleDoctor> horario) {
        this.activity = activity;
        this.horario = horario;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return horario.size();
    }

    @Override
    public long getItemId(int i){
        return horario.get(i).getId();
    }

    @Override
    public ScheduleDoctor getItem(int i) {
        return horario.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = inflater.inflate(R.layout.spinner_row, parent, false);

        TextView spinnerItem = (TextView) view.findViewById(R.id.spinnerItem);
        spinnerItem.setText(horario.get(position).getAvailability_date() + R.string.a_hor_mensaje + horario.get(position).getSchedule().getStart_hour() + " " + horario.get(position).getSchedule().getFinish_hour());

        return view;
    }
}
