package com.mind2web.vb360.modeles;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class ChatData {

   @SerializedName("conversations")
   List<Conversations> conversations;

   @SerializedName("chat_user_id")
   String chatUserId;


    public void setConversations(List<Conversations> conversations) {
        this.conversations = conversations;
    }
    public List<Conversations> getConversations() {
        return conversations;
    }
    
    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }
    public String getChatUserId() {
        return chatUserId;
    }
    
}