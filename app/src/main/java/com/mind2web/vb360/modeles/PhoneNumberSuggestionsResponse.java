package com.mind2web.vb360.modeles;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class PhoneNumberSuggestionsResponse {

   @SerializedName("status")
   boolean status;

   @SerializedName("message")
   String message;

   @SerializedName("data")
   List<PhoneNumberSuggestedData> data;

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
    
    public void setData(List<PhoneNumberSuggestedData> data) {
        this.data = data;
    }
    public List<PhoneNumberSuggestedData> getData() {
        return data;
    }
    
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
    public Pagination getPagination() {
        return pagination;
    }
    
}