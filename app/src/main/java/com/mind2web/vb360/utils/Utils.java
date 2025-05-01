package com.mind2web.vb360.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mind2web.vb360.LoginActivity;

public class Utils {
    public static void logoutApp(Activity context){
        UserSharedPreferences.getInstance(context).clearSharePreferences();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        context.startActivity(intent);
        context.finish();
    }
}
