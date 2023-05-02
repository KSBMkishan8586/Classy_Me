package com.ksbm.ontu.api_client;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ksbm.ontu.utils.Connectivity;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Raghvendra Sahu on 20-Apr-21.
 */
public class RxApiClient {
    static Context mcontext;

    public static Retrofit getClient(String baseUrl) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

//        OkHttpClient client = new OkHttpClient.Builder()
//                //.connectionSpecs(tlsSpecs)
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100, TimeUnit.SECONDS)
//                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    //*****************retrofit cache store***********

    public static Retrofit getCacheEnabledRetrofit(String baseUrl, Context context) {
        mcontext = context;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
                .addInterceptor(OFFLINE_INTERCEPTOR)
                .addNetworkInterceptor(ONLINE_INTERCEPTOR)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    static Interceptor ONLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    static Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Connectivity.isConnected(mcontext)) {
                int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return chain.proceed(request);
        }
    };


}
