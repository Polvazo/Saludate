package com.saludate.saludate.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.saludate.saludate.Fragments.appointmentFragment;
import com.saludate.saludate.Fragments.appointmentHistoryFragment;
import com.saludate.saludate.Fragments.userDataFragment;

/**
 * Created by suraj on 23/6/17.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                appointmentFragment appointmentFragment =new appointmentFragment();
                return appointmentFragment;
            case 1 :
                appointmentHistoryFragment appointmentHistoryFragment =new appointmentHistoryFragment();
                return appointmentHistoryFragment;
            case 2 :
                userDataFragment userDataFragment =new userDataFragment();
                return userDataFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
