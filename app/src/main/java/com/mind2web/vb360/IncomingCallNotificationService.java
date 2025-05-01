package com.mind2web.vb360;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.mind2web.vb360.utils.UserSharedPreferences;
import com.twilio.voice.CallInvite;
import com.twilio.voice.CancelledCallInvite;

public class IncomingCallNotificationService extends Service {

    private static final String TAG = IncomingCallNotificationService.class.getSimpleName();
    int notificationId ;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            CallInvite callInvite = intent.getParcelableExtra(UserSharedPreferences.INCOMING_CALL_INVITE);
             notificationId = intent.getIntExtra(UserSharedPreferences.INCOMING_CALL_NOTIFICATION_ID, 0);
            switch (action) {
                case UserSharedPreferences.ACTION_INCOMING_CALL:
                    handleIncomingCall(intent, callInvite, notificationId);
                    break;
                case UserSharedPreferences.ACTION_ACCEPT:
                    accept(callInvite, notificationId);
                    break;
                case UserSharedPreferences.ACTION_REJECT:
                    reject(callInvite);
                    break;
                case UserSharedPreferences.ACTION_CANCEL_CALL:
                    handleCancelledCall(intent);
                    break;
                default:
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification(CallInvite callInvite, int notificationId, int channelImportance) {
        Intent intent = new Intent(this, VoiceActivity.class);
        intent.putExtra("phone",callInvite.getFrom());
        intent.putExtra("incoming",true);


        intent.setAction(UserSharedPreferences.ACTION_INCOMING_CALL_NOTIFICATION);
        intent.putExtra(UserSharedPreferences.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        intent.putExtra(UserSharedPreferences.INCOMING_CALL_INVITE, callInvite);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_IMMUTABLE);
        /*
         * Pass the notification id and call sid to use as an identifier to cancel the
         * notification later
         */
        Bundle extras = new Bundle();
        extras.putString(UserSharedPreferences.CALL_SID_KEY, callInvite.getCallSid());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return buildNotification(callInvite.getFrom() + " is calling.",
                    pendingIntent,
                    extras,
                    callInvite,
                    notificationId,
                    createChannel(channelImportance));
        } else {
            //noinspection deprecation
            return new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_call_end_white_24dp)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(callInvite.getFrom() + " is calling.")
                    .setAutoCancel(true)
                    .setExtras(extras)
                    .setContentIntent(pendingIntent)
//                    .setGroup("test_app_notification")
                    .setCategory(Notification.CATEGORY_CALL)
                    .setColor(Color.rgb(214, 10, 37)).build();
        }
    }

    /**
     * Build a notification.
     *
     * @param text          the text of the notification
     * @param pendingIntent the body, pending intent for the notification
     * @param extras        extras passed with the notification
     * @return the builder
     */
    @TargetApi(Build.VERSION_CODES.O)
    private Notification buildNotification(String text, PendingIntent pendingIntent, Bundle extras,
                                           final CallInvite callInvite,
                                           int notificationId,
                                           String channelId) {
        Intent rejectIntent = new Intent(getApplicationContext(), VoiceActivity.class);
        rejectIntent.putExtra("phone",callInvite.getFrom());
        rejectIntent.putExtra("incoming",true);
        rejectIntent.setAction(UserSharedPreferences.ACTION_REJECT);
        rejectIntent.putExtra(UserSharedPreferences.INCOMING_CALL_INVITE, callInvite);
        rejectIntent.putExtra(UserSharedPreferences.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        PendingIntent piRejectIntent = PendingIntent.getActivity(getApplicationContext(), notificationId, rejectIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent acceptIntent = new Intent(getApplicationContext(), VoiceActivity.class);
        acceptIntent.putExtra("phone",callInvite.getFrom());
        acceptIntent.putExtra("incoming",true);
        acceptIntent.setAction(UserSharedPreferences.ACTION_ACCEPT);
        acceptIntent.putExtra(UserSharedPreferences.INCOMING_CALL_INVITE, callInvite);
        acceptIntent.putExtra(UserSharedPreferences.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        acceptIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent piAcceptIntent = PendingIntent.getActivity(getApplicationContext(), notificationId, acceptIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder =
                new Notification.Builder(getApplicationContext(), channelId)
                        .setSmallIcon(R.drawable.ic_call_end_white_24dp)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(text)
                        .setCategory(Notification.CATEGORY_CALL)
                        .setExtras(extras)
                        .setAutoCancel(true)
                        .addAction(android.R.drawable.ic_menu_delete, getString(R.string.decline), piRejectIntent)
                        .addAction(android.R.drawable.ic_menu_call, getString(R.string.answer), piAcceptIntent)
                        .setFullScreenIntent(pendingIntent, true);

        return builder.build();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private String createChannel(int channelImportance) {
        NotificationChannel callInviteChannel = new NotificationChannel(UserSharedPreferences.VOICE_CHANNEL_HIGH_IMPORTANCE,
                "Primary Voice Channel", NotificationManager.IMPORTANCE_HIGH);
        String channelId = UserSharedPreferences.VOICE_CHANNEL_HIGH_IMPORTANCE;

        if (channelImportance == NotificationManager.IMPORTANCE_LOW) {
            callInviteChannel = new NotificationChannel(UserSharedPreferences.VOICE_CHANNEL_LOW_IMPORTANCE,
                    "Primary Voice Channel", NotificationManager.IMPORTANCE_LOW);
            channelId = UserSharedPreferences.VOICE_CHANNEL_LOW_IMPORTANCE;
        }
        callInviteChannel.setLightColor(Color.GREEN);
        callInviteChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(callInviteChannel);

        return channelId;
    }

    private void accept(CallInvite callInvite, int notificationId) {
        endForeground();
    }

    private void reject(CallInvite callInvite) {
        endForeground();
        callInvite.reject(getApplicationContext());
    }

    private void handleCancelledCall(Intent intent) {
        endForeground();
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        CancelledCallInvite callInvite = intent.getParcelableExtra(UserSharedPreferences.CANCELLED_CALL_INVITE);
        if (callInvite != null) {
            showMissedCallNotification(callInvite);
        }

    }

    private void handleIncomingCall(Intent intent, CallInvite callInvite, int notificationId) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


        setCallInProgressNotification(callInvite, notificationId);
    }


    private void endForeground() {
        stopForeground(true);
    }


    private void showMissedCallNotification(CancelledCallInvite cancelCallInvite) {
        String channelId = "missed_call_channel";
        String channelName = "Missed Call Notifications";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notifications for missed calls");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }


        Intent acceptIntent = new Intent(getApplicationContext(), MainActivity.class);
        acceptIntent.setAction(UserSharedPreferences.ACTION_CANCEL_CALL);
        acceptIntent.putExtra(UserSharedPreferences.CANCELLED_CALL_INVITE, cancelCallInvite);
        acceptIntent.putExtra(UserSharedPreferences.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        acceptIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent piCancelIntent = PendingIntent.getActivity(getApplicationContext(), notificationId, acceptIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_call_missed)
                .setContentTitle("Missed Call")
                .setContentText("Missed call from " + cancelCallInvite.getFrom())
                .setAutoCancel(true)
                .setContentIntent(piCancelIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MISSED_CALL)
                .setColor(Color.RED);

        if (notificationManager != null) {
            notificationManager.notify(1001, builder.build()); // Use a unique ID for the missed call notification
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void setCallInProgressNotification(CallInvite callInvite, int notificationId) {
        if (isAppVisible()) {
            Log.i(TAG, "setCallInProgressNotification - app is visible.");
            showHighPriorityNotification(callInvite,notificationId);
        } else {
            Log.i(TAG, "setCallInProgressNotification - app is NOT visible.");
            showHighPriorityNotification(callInvite,notificationId);

        }
//
//        Intent intent = new Intent(this, VoiceActivity.class);
//        intent.setAction(UserSharedPreferences.ACTION_INCOMING_CALL_NOTIFICATION);
//        intent.putExtra(UserSharedPreferences.INCOMING_CALL_NOTIFICATION_ID, notificationId);
//        intent.putExtra(UserSharedPreferences.INCOMING_CALL_INVITE, callInvite);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }


    private void showHighPriorityNotification(CallInvite callInvite, int notificationId) {
        Notification notification = createNotification(callInvite, notificationId, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notification);
        }
    }

    private void startForegroundService(final int id, final Notification notification) {
        startForegroundServiceSafely(id,notification);
    }

    private void startForegroundServiceSafely(final int id, final Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                startForeground(id, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_PHONE_CALL);
            } catch (SecurityException e) {
                Log.e(TAG, "Permission error starting foreground service", e);
            }
        } else {
            startForeground(id, notification);
        }
    }

    private boolean isAppVisible() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
    }


}
