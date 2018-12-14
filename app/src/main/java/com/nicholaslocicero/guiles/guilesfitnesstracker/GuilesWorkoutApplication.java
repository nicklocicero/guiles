package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class GuilesWorkoutApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
