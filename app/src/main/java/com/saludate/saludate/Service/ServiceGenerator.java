package com.saludate.saludate.Service;

import com.saludate.saludate.Constans.Contants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();



    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Contants.URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass)
    {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


}
