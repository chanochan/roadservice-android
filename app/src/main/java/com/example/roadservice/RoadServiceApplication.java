package com.example.roadservice;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class RoadServiceApplication extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getAppContext() {
        return RoadServiceApplication.context;
    }

    public void onCreate() {
        super.onCreate();
        RoadServiceApplication.context = getApplicationContext();
    }
}
