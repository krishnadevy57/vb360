package com.mind2web.vb360;

import android.app.Application;
import android.util.Log;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Register the lifecycle tracker
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        FirebaseApp.initializeApp(this);
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
            Log.d("FirebaseInit", "Firebase initialized");
        } else {
            Log.d("FirebaseInit", "Firebase already initialized");
        }
    }
}


