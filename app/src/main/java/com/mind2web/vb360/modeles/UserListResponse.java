package com.mind2web.vb360.modeles;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserListResponse {
    public static UserListResponse fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserListResponse.class);
    }



    @SerializedName("status")
    private Boolean status;

    @SerializedName("data")
    private ArrayList<UserData> data;

    @SerializedName("message")
    private String message;

    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("status_code")
    private int statusCode;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<UserData> getData() {
        return data;
    }

    public void setData(ArrayList<UserData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
