package com.metaintellect.tina;

import android.app.Application;
import android.content.Context;

public class Tina extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        Tina.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Tina.context;
    }
}