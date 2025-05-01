package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class PhoneNumberSuggestedData {

   @SerializedName("custom_user_name")
   String customUserName;

   @SerializedName("user_short_form")
   String userShortForm;

   @SerializedName("user_full_name")
   String userFullName;

   @SerializedName("label")
   String label;

   @SerializedName("value")
   String value;

   @SerializedName("phone")
   String phone;

   @SerializedName("email")
   String email;

   @SerializedName("customerID")
   String customerID;


    public void setCustomUserName(String customUserName) {
        this.customUserName = customUserName;
    }
    public String getCustomUserName() {
        return customUserName;
    }
    
    public void setUserShortForm(String userShortForm) {
        this.userShortForm = userShortForm;
    }
    public String getUserShortForm() {
        return userShortForm;
    }
    
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
    public String getUserFullName() {
        return userFullName;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public String getCustomerID() {
        return customerID;
    }
    
}