package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getUnreadEmailCount() {
        return unreadEmailCount;
    }

    public void setUnreadEmailCount(String unreadEmailCount) {
        this.unreadEmailCount = unreadEmailCount;
    }

    public String getUnreadSmsCount() {
        return unreadSmsCount;
    }

    public void setUnreadSmsCount(String unreadSmsCount) {
        this.unreadSmsCount = unreadSmsCount;
    }

    public String getUnreadCallCount() {
        return unreadCallCount;
    }

    public void setUnreadCallCount(String unreadCallCount) {
        this.unreadCallCount = unreadCallCount;
    }

    public String getLastChatType() {
        return lastChatType;
    }

    public void setLastChatType(String lastChatType) {
        this.lastChatType = lastChatType;
    }

    public String getLastConversationTime() {
        return lastConversationTime;
    }

    public void setLastConversationTime(String lastConversationTime) {
        this.lastConversationTime = lastConversationTime;
    }

    public String getFinalLastConversationTime() {
        return finalLastConversationTime;
    }

    public void setFinalLastConversationTime(String finalLastConversationTime) {
        this.finalLastConversationTime = finalLastConversationTime;
    }

    public String getCustomUserName() {
        return customUserName;
    }

    public void setCustomUserName(String customUserName) {
        this.customUserName = customUserName;
    }

    public String getUserShortForm() {
        return userShortForm;
    }

    public void setUserShortForm(String userShortForm) {
        this.userShortForm = userShortForm;
    }

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("unread_count")
    private String unreadCount;

    @SerializedName("unread_email_count")
    private String unreadEmailCount;

    @SerializedName("unread_sms_count")
    private String unreadSmsCount;

    @SerializedName("unread_call_count")
    private String unreadCallCount;

    @SerializedName("last_chat_type")
    private String lastChatType;

    @SerializedName("last_conversation_time")
    private String lastConversationTime;

    @SerializedName("final_last_conversation_time")
    private String finalLastConversationTime;

    @SerializedName("custom_user_name")
    private String customUserName;

    @SerializedName("user_short_form")
    private String userShortForm;

    // Getters and setters for all fields
}
