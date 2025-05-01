package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class TwilioCallingToken {

   @SerializedName("twilio_access_token")
   String twilioAccessToken;

   @SerializedName("twilio_token_expiration_time")
   String twilioTokenExpirationTime;

   @SerializedName("device_token")
   String deviceToken;

   @SerializedName("fcm_token")
   String fcmToken;


    public void setTwilioAccessToken(String twilioAccessToken) {
        this.twilioAccessToken = twilioAccessToken;
    }
    public String getTwilioAccessToken() {
        return twilioAccessToken;
    }
    
    public void setTwilioTokenExpirationTime(String twilioTokenExpirationTime) {
        this.twilioTokenExpirationTime = twilioTokenExpirationTime;
    }
    public String getTwilioTokenExpirationTime() {
        return twilioTokenExpirationTime;
    }
    
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
    public String getDeviceToken() {
        return deviceToken;
    }
    
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
    public String getFcmToken() {
        return fcmToken;
    }
    
}