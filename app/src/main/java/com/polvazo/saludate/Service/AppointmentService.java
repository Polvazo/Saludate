package com.polvazo.saludate.Service;

import com.polvazo.saludate.Models.General;
import com.polvazo.saludate.Models.appointmentProcess;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppointmentService {

    @GET("patient/appointment-api")
    Call<List<General>> getAppointment (@Query("id_user") Integer id_user);

    @GET("patient/medical-record-api/{id}")
    Call<General> getMedicalRecord (@Path("") Integer id_user);

    @FormUrlEncoded
    @POST("patient/appointment-api/")
    Call<ResponseBody> crearNuevaCita(
            //@Field("id") Integer id,
            @Field("schedule_doctor") Integer schedule_doctor,
            @Field("speciality_doctor") Integer speciality_doctor,
            @Field("patient") Integer patient,
            @Field("description") String description,
            @Field("annotations") String annotations,
            @Field("status") String status
    );

    @PATCH("patient/appointment-api/{id}/")
    Call<ResponseBody> modificarCita (@Path("id") Integer id_user, @Body General General);

    @PUT("patient/appointment-api/{id}/")
    Call<ResponseBody> cambiarCita (@Path("id") Integer id_user, @Body appointmentProcess appointmentProcess);


}
