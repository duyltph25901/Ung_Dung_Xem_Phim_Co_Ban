package com.example.appmovie.class_supported;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManagement.init(getApplicationContext());
    }
}
