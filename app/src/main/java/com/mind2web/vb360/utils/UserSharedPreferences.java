package com.mind2web.vb360.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class UserSharedPreferences {

    private static final String PREF_NAME = "user_shared_preferences";

    private static UserSharedPreferences instance;
    private static SharedPreferences sharedPreferences = null;

    public static final String PREF_USER_AUTH_TOKEN = "user_auth_token_pref";
    public static final String PREF_USER_PHONE_NUMBER = "user_phone_number_pref";
    public static final String PREF_USER_EMAIL_NUMBER = "user_email_pref";
    public static final String PREF_USER_AUTH_USER_ID = "user_auth_user_id_pref";
    public static final String PREF_TWILIO_AUTH_TOKEN = "user_twilio_user_auth_token";
    public static final String PREF_IS_USER_LOGGEDIN = "isLoggedIn";


    public static final String CALL_SID_KEY = "CALL_SID";
    public static final String VOICE_CHANNEL_LOW_IMPORTANCE = "notification-channel-low-importance";
    public static final String VOICE_CHANNEL_HIGH_IMPORTANCE = "notification-channel-high-importance";
    public static final String OUTGOING_CALL_RECIPIENT = "OUTGOING_CALL_RECIPIENT";
    public static final String INCOMING_CALL_INVITE = "INCOMING_CALL_INVITE";
    public static final String CANCELLED_CALL_INVITE = "CANCELLED_CALL_INVITE";
    public static final String INCOMING_CALL_NOTIFICATION_ID = "INCOMING_CALL_NOTIFICATION_ID";
    public static final String ACTION_ACCEPT = "ACTION_ACCEPT";
    public static final String ACTION_REJECT = "ACTION_REJECT";
    public static final String ACTION_INCOMING_CALL_NOTIFICATION = "ACTION_INCOMING_CALL_NOTIFICATION";
    public static final String ACTION_INCOMING_CALL = "ACTION_INCOMING_CALL";
    public static final String ACTION_OUTGOING_CALL = "ACTION_OUTGOING_CALL";
    public static final String ACTION_CANCEL_CALL = "ACTION_CANCEL_CALL";
    public static final String ACTION_FCM_TOKEN = "ACTION_FCM_TOKEN";
    public static final String ACTION_CALL_STATUS = "ACTION_CALL_STATUS";

    public static final String TAG = "VoiceActivity";
    public static final String ACTION_DISCONNECT_CALL = "ACTION_DISCONNECT_CALL";
    public static final String ACTION_DTMF_SEND = "ACTION_DTMF_SEND";
    public static final String DTMF = "DTMF";
    public static final int PERMISSIONS_ALL = 100;


    public static String authToken;
    public static String twilioAuthToken;
    public static boolean isLoggedIn = false;
    public static String userId;
    public static String userPhone;
    public static String userEmail;

    private UserSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserSharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new UserSharedPreferences(context);
        }
        getUserPreferences();
        return instance;
    }

    public void saveIntPreferences(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void saveStringPreferences(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void saveDoublePreferences(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public void saveBoolPreferences(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void saveStringListPreferences(String key, Set<String> value) {
        sharedPreferences.edit().putStringSet(key, value).apply();
    }

    public int getIntPreferences(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public String getStringPreferences(String key) {
        return sharedPreferences.getString(key, null);
    }

    public float getDoublePreferences(String key) {
        return sharedPreferences.getFloat(key, -1);
    }

    public boolean getBoolPreferences(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public Set<String> getStringListPreferences(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    public void removeSharePreference(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clearSharePreferences() {
        sharedPreferences.edit().clear().apply();
    }

    public static void getUserPreferences() {
         authToken = sharedPreferences.getString(PREF_USER_AUTH_TOKEN, "");
         twilioAuthToken = sharedPreferences.getString(PREF_TWILIO_AUTH_TOKEN, "");
         isLoggedIn = sharedPreferences.getBoolean(PREF_IS_USER_LOGGEDIN, false);
         userId = sharedPreferences.getString(PREF_USER_AUTH_USER_ID, "");
        userEmail = sharedPreferences.getString(PREF_USER_EMAIL_NUMBER, "");
        userPhone = sharedPreferences.getString(PREF_USER_PHONE_NUMBER, "");
    }

    public void setUserPreferences(String userId, String authToken, String twilioAuthToken, boolean isLoggedIn,String email,String phone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (authToken != null) {
            editor.putString(PREF_USER_AUTH_TOKEN, authToken);
        }

        editor.putString(PREF_USER_AUTH_USER_ID, userId);

        if (twilioAuthToken != null) {
            editor.putString(PREF_TWILIO_AUTH_TOKEN, twilioAuthToken);
        }

        if (email != null) {
            editor.putString(PREF_USER_EMAIL_NUMBER, email);
        }

        if (phone != null) {
            editor.putString(PREF_USER_PHONE_NUMBER, phone);
        }

        editor.putBoolean(PREF_IS_USER_LOGGEDIN, isLoggedIn);
        editor.apply();
        getUserPreferences();
    }
}