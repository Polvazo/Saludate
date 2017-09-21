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

public class especialidadAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Speciality> especialidad;
    private LayoutInflater inflater;

    public especialidadAdapter(Activity activity, ArrayList<Speciality> especialidad) {
        this.activity=activity;
        this.especialidad=especialidad;
        inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return especialidad.size();
    }

    @Override
    public long getItemId(int i){
        return especialidad.get(i).getId();
    }

    @Override
    public Speciality getItem(int i) {
        return especialidad.get(i);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = inflater.inflate(R.layout.spinner_row, parent, false);

        TextView spinnerItem=(TextView) view.findViewById(R.id.spinnerItem);
        spinnerItem.setText(especialidad.get(position).getName());

        return view;
    }
}
