package com.ksbm.ontu.utils;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apis {


    public static Retrofit postApiClient() {
        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://classyme.org/index.php/apis/")
                .client(getclient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private static OkHttpClient getclient() {
        return new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }
}
