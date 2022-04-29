package com.example.courier.ApiCaller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCaller {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.8.115:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
