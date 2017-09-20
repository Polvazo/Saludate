package com.polvazo.saludate.Service;


import com.polvazo.saludate.Models.Speciality;
import com.polvazo.saludate.Models.SpecialityDoctor;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.http.GET;


public interface doctorService {

    @GET("/doctor/speciality-doctor-api")
    Call<ArrayList<SpecialityDoctor>> getSpecialityDoctor();

    @GET("/doctor/speciality-api/")
    Call<ArrayList<Speciality>> getSpeciality();
}
