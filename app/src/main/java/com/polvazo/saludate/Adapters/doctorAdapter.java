package com.polvazo.saludate.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polvazo.saludate.Models.Speciality;
import com.polvazo.saludate.Models.SpecialityDoctor;
import com.polvazo.saludate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 20/09/2017.
 */

public class doctorAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<SpecialityDoctor> doctors;
    private LayoutInflater inflater;

    public doctorAdapter(Activity activity, ArrayList<SpecialityDoctor> doctors){
        this.activity=activity;
        this.doctors=doctors;
        inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return doctors.size();
    }

    @Override
    public long getItemId(int i){
        return doctors.get(i).getId();
    }

    @Override
    public SpecialityDoctor getItem(int i) {
        return doctors.get(i);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.spinner_row, null);


        TextView spinnerItem=(TextView) view.findViewById(R.id.spinnerItem);
        spinnerItem.setText(doctors.get(position).getDoctor().getPerson().getUser().getFirst_name()+" "+doctors.get(position).getDoctor().getPerson().getUser().getLast_name());

        return view;
    }
}
