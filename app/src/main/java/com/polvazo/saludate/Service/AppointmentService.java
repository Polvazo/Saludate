package com.polvazo.saludate.Service;

import com.polvazo.saludate.Models.General;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppointmentService {

    @GET("/patient/appointment-api")
    Call<List<General>> getAppointment (@Query("id_user") Integer id_user);
}
