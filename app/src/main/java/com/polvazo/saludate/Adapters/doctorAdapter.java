package com.polvazo.saludate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.polvazo.saludate.Models.Speciality;
import com.polvazo.saludate.Models.SpecialityDoctor;
import com.polvazo.saludate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 20/09/2017.
 */

public class doctorAdapter extends ArrayAdapter<SpecialityDoctor> {

    private Context context;
    private ArrayList<SpecialityDoctor> generallist;

    public doctorAdapter(Context context, int resource, ArrayList<SpecialityDoctor> object) {
        super(context, resource, object);
        this.context = context;
        this.generallist = object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_row, parent, false);

        SpecialityDoctor cita = generallist.get(position);

        TextView spinnerItem=(TextView) view.findViewById(R.id.spinnerItem);
        spinnerItem.setText(cita.getDoctor().getPerson().getUser().getFirst_name()+" "+cita.getDoctor().getPerson().getUser().getLast_name());

        return view;
    }
}
