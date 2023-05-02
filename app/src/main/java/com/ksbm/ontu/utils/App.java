package com.ksbm.ontu.utils;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.danikula.videocache.HttpProxyCacheServer;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.main_screen.MainActivity;

public class App extends Application {


    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)
                .maxCacheFilesCount(20)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
