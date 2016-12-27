package com.eflake.keyanimengine.main;

import android.app.Application;

import com.karumi.dexter.Dexter;


public class KeyFrameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
    }
}
