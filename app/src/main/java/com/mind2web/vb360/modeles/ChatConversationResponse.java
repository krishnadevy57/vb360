package com.mind2web.vb360.modeles;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

   
public class ChatConversationResponse {
    public static ChatConversationResponse fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ChatConversationResponse.class);
    }
   @SerializedName("status")
   boolean status;

   @SerializedName("message")
   String message;

   @SerializedName("data")
   ChatData data;

   @SerializedName("pagination")
   Pagination pagination;

    @SerializedName("status_code")
    private int statusCode;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    
    public void setData(ChatData data) {
        this.data = data;
    }
    public ChatData getData() {
        return data;
    }
    
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
    public Pagination getPagination() {
        return pagination;
    }
    
}