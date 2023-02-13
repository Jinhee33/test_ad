package com.example.test;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static String BASE_URL = "http://192.168.100.35:8282";

    public static RetrofitService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(RetrofitService.class);
    }
}
