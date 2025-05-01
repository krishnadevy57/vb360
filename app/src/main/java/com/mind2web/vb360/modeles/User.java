package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class User {

   @SerializedName("user_id")
   String userId;

   @SerializedName("contactId")
   String contactId;

   @SerializedName("email")
   String email;

   @SerializedName("username")
   String username;

   @SerializedName("admin")
   String admin;

   @SerializedName("first_name")
   String firstName;

   @SerializedName("last_name")
   String lastName;

   @SerializedName("phone")
   String phone;

   @SerializedName("website")
   String website;

   @SerializedName("company")
   String company;

   @SerializedName("last_login")
   String lastLogin;

   @SerializedName("billing_id")
   String billingId;

   @SerializedName("shipping_id")
   String shippingId;

   @SerializedName("mark_up")
   String markUp;

   @SerializedName("user_type")
   String userType;

   @SerializedName("cc_permission")
   String ccPermission;

   @SerializedName("status")
   String status;

   @SerializedName("employee_type")
   String employeeType;

   @SerializedName("profile_image")
   String profileImage;

   @SerializedName("full_price_shipping")
   String fullPriceShipping;

   @SerializedName("twilio_identity")
   String twilioIdentity;

   @SerializedName("voice_mail_message")
   String voiceMailMessage;

   @SerializedName("voice_mail_length")
   String voiceMailLength;

   @SerializedName("voice_message_gender")
   String voiceMessageGender;

   @SerializedName("assigned_phone_number")
   String assignedPhoneNumber;

   @SerializedName("twilio_account_details")
   TwilioAccountDetails twilioAccountDetails;

   @SerializedName("twilio_calling_token")
   TwilioCallingToken twilioCallingToken;


    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    public String getContactId() {
        return contactId;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public String getAdmin() {
        return admin;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getWebsite() {
        return website;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }
    
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
    public String getLastLogin() {
        return lastLogin;
    }
    
    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }
    public String getBillingId() {
        return billingId;
    }
    
    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }
    public String getShippingId() {
        return shippingId;
    }
    
    public void setMarkUp(String markUp) {
        this.markUp = markUp;
    }
    public String getMarkUp() {
        return markUp;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType() {
        return userType;
    }
    
    public void setCcPermission(String ccPermission) {
        this.ccPermission = ccPermission;
    }
    public String getCcPermission() {
        return ccPermission;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    
    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
    public String getEmployeeType() {
        return employeeType;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setFullPriceShipping(String fullPriceShipping) {
        this.fullPriceShipping = fullPriceShipping;
    }
    public String getFullPriceShipping() {
        return fullPriceShipping;
    }
    
    public void setTwilioIdentity(String twilioIdentity) {
        this.twilioIdentity = twilioIdentity;
    }
    public String getTwilioIdentity() {
        return twilioIdentity;
    }
    
    public void setVoiceMailMessage(String voiceMailMessage) {
        this.voiceMailMessage = voiceMailMessage;
    }
    public String getVoiceMailMessage() {
        return voiceMailMessage;
    }
    
    public void setVoiceMailLength(String voiceMailLength) {
        this.voiceMailLength = voiceMailLength;
    }
    public String getVoiceMailLength() {
        return voiceMailLength;
    }
    
    public void setVoiceMessageGender(String voiceMessageGender) {
        this.voiceMessageGender = voiceMessageGender;
    }
    public String getVoiceMessageGender() {
        return voiceMessageGender;
    }
    
    public void setAssignedPhoneNumber(String assignedPhoneNumber) {
        this.assignedPhoneNumber = assignedPhoneNumber;
    }
    public String getAssignedPhoneNumber() {
        return assignedPhoneNumber;
    }
    
    public void setTwilioAccountDetails(TwilioAccountDetails twilioAccountDetails) {
        this.twilioAccountDetails = twilioAccountDetails;
    }
    public TwilioAccountDetails getTwilioAccountDetails() {
        return twilioAccountDetails;
    }
    
    public void setTwilioCallingToken(TwilioCallingToken twilioCallingToken) {
        this.twilioCallingToken = twilioCallingToken;
    }
    public TwilioCallingToken getTwilioCallingToken() {
        return twilioCallingToken;
    }
    
}