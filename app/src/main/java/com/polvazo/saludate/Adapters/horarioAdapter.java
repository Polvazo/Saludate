package com.polvazo.saludate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.polvazo.saludate.Models.ScheduleDoctor;
import com.polvazo.saludate.Models.SpecialityDoctor;
import com.polvazo.saludate.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 21/09/2017.
 */

public class horarioAdapter extends ArrayAdapter<ScheduleDoctor> {
    private Context context;
    private ArrayList<ScheduleDoctor> generallist;

    public horarioAdapter(Context context, int resource, ArrayList<ScheduleDoctor> object) {
        super(context, resource, object);
        this.context = context;
        this.generallist = object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_row, parent, false);

        ScheduleDoctor cita = generallist.get(position);

        TextView spinnerItem=(TextView) view.findViewById(R.id.spinnerItem);
        spinnerItem.setText(cita.getAvailability_date()+R.string.a_hor_mensaje+cita.getSchedule().getStart_hour()+" "+cita.getSchedule().getFinish_hour());

        return view;
    }
}
