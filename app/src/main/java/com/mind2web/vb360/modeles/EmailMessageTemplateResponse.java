package com.mind2web.vb360.modeles;

import java.util.ArrayList;

public class EmailMessageTemplateResponse{
    public boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<EmailMessageTemplateData> getData() {
        return data;
    }

    public void setData(ArrayList<EmailMessageTemplateData> data) {
        this.data = data;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String message;
    public ArrayList<EmailMessageTemplateData> data;
    public int status_code;
}