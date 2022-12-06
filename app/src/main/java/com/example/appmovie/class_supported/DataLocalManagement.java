package com.example.appmovie.class_supported;

import android.content.Context;

public class DataLocalManagement {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static DataLocalManagement instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManagement();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManagement getInstance() {
        if (instance == null) {
            instance = new DataLocalManagement();
        }

        return instance;
    }

    public static void setFirstInstall(boolean isFirstInstall) {
        DataLocalManagement.getInstance().mySharedPreferences.putBooleanValue(PREF_FIRST_INSTALL, isFirstInstall);
    }

    public static boolean getFirstInstall() {
        return DataLocalManagement.getInstance().mySharedPreferences.getBooleanValue(PREF_FIRST_INSTALL);
    }
}
