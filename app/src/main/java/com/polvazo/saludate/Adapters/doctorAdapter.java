package com.polvazo.saludate.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polvazo.saludate.Models.Doctor;
import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.Models.Speciality;
import com.polvazo.saludate.Models.SpecialityDoctor;
import com.polvazo.saludate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 18/09/2017.
 */

public class doctorAdapter extends ArrayAdapter<Speciality> {

    private Context context;
    private ArrayList<Speciality> generallist;

    public doctorAdapter(Context context, int resource, ArrayList<Speciality> object) {
        super(context, resource, object);
        this.context = context;
        this.generallist = object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_row, parent, false);

        Speciality cita = generallist.get(position);

        TextView spinnerItem=(TextView) view.findViewById(R.id.spinnerItem);
        spinnerItem.setText(cita.getName());

        return view;
    }
}
