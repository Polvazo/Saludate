package com.saludate.saludate.Service;

import com.saludate.saludate.Models.userNotification;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by USUARIO on 24/09/2017.
 */

public interface notificationService {

    @GET("user-device-api")
    Call<ArrayList<userNotification>> getNotification(@Query("id_person") Integer id_person);

    @FormUrlEncoded
    @POST("user-device-api/")
    Call<ResponseBody> postNotification(
            @Field("person") Integer id_person,
            @Field("id_device") String id_device
    );

}
