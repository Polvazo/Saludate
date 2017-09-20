package com.polvazo.saludate.Service;


import com.polvazo.saludate.Models.userLogin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface userService {

    @GET("/login")
    Call<userLogin> getStatus (@Query("username") String username,
                               @Query("password") String password);
}
