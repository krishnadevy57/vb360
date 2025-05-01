package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class TwilioAccountDetails {

   @SerializedName("id")
   String id;

   @SerializedName("sid")
   String sid;

   @SerializedName("auth_token")
   String authToken;

   @SerializedName("friendly_name")
   String friendlyName;

   @SerializedName("service_id")
   String serviceId;

   @SerializedName("twilio_messaging_service_name")
   String twilioMessagingServiceName;

   @SerializedName("twiml_sid")
   String twimlSid;

   @SerializedName("twiml_friendly_name")
   String twimlFriendlyName;

   @SerializedName("twilio_api_key")
   String twilioApiKey;

   @SerializedName("twilio_api_secret")
   String twilioApiSecret;

   @SerializedName("twilio_api_friendly_name")
   String twilioApiFriendlyName;

   @SerializedName("twilio_api_date_created")
   String twilioApiDateCreated;

   @SerializedName("twilio_api_date_updated")
   String twilioApiDateUpdated;

   @SerializedName("status")
   String status;

   @SerializedName("date_created")
   String dateCreated;

   @SerializedName("type")
   String type;

   @SerializedName("user_id")
   String userId;

   @SerializedName("created_at")
   String createdAt;

   @SerializedName("updated_at")
   String updatedAt;


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getSid() {
        return sid;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getAuthToken() {
        return authToken;
    }
    
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
    public String getFriendlyName() {
        return friendlyName;
    }
    
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getServiceId() {
        return serviceId;
    }
    
    public void setTwilioMessagingServiceName(String twilioMessagingServiceName) {
        this.twilioMessagingServiceName = twilioMessagingServiceName;
    }
    public String getTwilioMessagingServiceName() {
        return twilioMessagingServiceName;
    }
    
    public void setTwimlSid(String twimlSid) {
        this.twimlSid = twimlSid;
    }
    public String getTwimlSid() {
        return twimlSid;
    }
    
    public void setTwimlFriendlyName(String twimlFriendlyName) {
        this.twimlFriendlyName = twimlFriendlyName;
    }
    public String getTwimlFriendlyName() {
        return twimlFriendlyName;
    }
    
    public void setTwilioApiKey(String twilioApiKey) {
        this.twilioApiKey = twilioApiKey;
    }
    public String getTwilioApiKey() {
        return twilioApiKey;
    }
    
    public void setTwilioApiSecret(String twilioApiSecret) {
        this.twilioApiSecret = twilioApiSecret;
    }
    public String getTwilioApiSecret() {
        return twilioApiSecret;
    }
    
    public void setTwilioApiFriendlyName(String twilioApiFriendlyName) {
        this.twilioApiFriendlyName = twilioApiFriendlyName;
    }
    public String getTwilioApiFriendlyName() {
        return twilioApiFriendlyName;
    }
    
    public void setTwilioApiDateCreated(String twilioApiDateCreated) {
        this.twilioApiDateCreated = twilioApiDateCreated;
    }
    public String getTwilioApiDateCreated() {
        return twilioApiDateCreated;
    }
    
    public void setTwilioApiDateUpdated(String twilioApiDateUpdated) {
        this.twilioApiDateUpdated = twilioApiDateUpdated;
    }
    public String getTwilioApiDateUpdated() {
        return twilioApiDateUpdated;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    
}