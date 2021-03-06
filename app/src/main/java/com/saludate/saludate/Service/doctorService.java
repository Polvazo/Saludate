package com.saludate.saludate.Service;


import com.saludate.saludate.Models.ScheduleDoctor;
import com.saludate.saludate.Models.Speciality;
import com.saludate.saludate.Models.SpecialityDoctor;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.http.GET;


public interface doctorService {

    @GET("doctor/speciality-doctor-api/")
    Call<ArrayList<SpecialityDoctor>> getSpecialityDoctor();

    @GET("doctor/speciality-api/")
    Call<ArrayList<Speciality>> getSpeciality();

    @GET("doctor/schedule-doctor-api/")
    Call<ArrayList<ScheduleDoctor>> getHorario();
}
