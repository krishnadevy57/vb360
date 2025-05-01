package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class LoginResponse {

   @SerializedName("status")
   String status;

   @SerializedName("message")
   String message;

   @SerializedName("user")
   User user;

   @SerializedName("access_token")
   String accessToken;


    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    
}