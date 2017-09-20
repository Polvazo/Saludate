package com.polvazo.saludate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.R;

import java.util.List;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class appointmentAdapter extends ArrayAdapter<General> {


    private Context context;
    private List<General> generallist;

    public appointmentAdapter(Context context, int resource, List<General> object) {
        super(context, resource, object);
        this.context = context;
        this.generallist = object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cita_adapter_list, parent, false);


        General cita = generallist.get(position);

        TextView especialidad = (TextView) view.findViewById(R.id.txt_speciality);
        especialidad.setText(cita.getSpeciality_doctor().getSpeciality().getName());

        TextView nombreDoctor = (TextView) view.findViewById(R.id.txt_doctor);
        nombreDoctor.setText(cita.schedule_doctor.getDoctor().getPerson().getUser().getFirst_name() + " " + cita.schedule_doctor.getDoctor().getPerson().getUser().getLast_name());

        TextView estado = (TextView) view.findViewById(R.id.txt_status);
        estado.setText(cita.getStatus());

        TextView data = (TextView) view.findViewById(R.id.text_data);
        data.setText(cita.getSchedule_doctor().getAvailability_date());

        TextView fecha = (TextView) view.findViewById(R.id.text_hora);
        fecha.setText(cita.getSchedule_doctor().getSchedule().getStart_hour());


        return view;
    }
}
