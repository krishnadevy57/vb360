package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class ParentCallDetails {

   @SerializedName("id")
   String id;

   @SerializedName("call_sid")
   String callSid;

   @SerializedName("parent_call_sid")
   String parentCallSid;

   @SerializedName("call_cost")
   String callCost;

   @SerializedName("price_unit")
   String priceUnit;

   @SerializedName("call_duration")
   String callDuration;

   @SerializedName("direction")
   String direction;


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    
    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }
    public String getCallSid() {
        return callSid;
    }
    
    public void setParentCallSid(String parentCallSid) {
        this.parentCallSid = parentCallSid;
    }
    public String getParentCallSid() {
        return parentCallSid;
    }
    
    public void setCallCost(String callCost) {
        this.callCost = callCost;
    }
    public String getCallCost() {
        return callCost;
    }
    
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }
    public String getPriceUnit() {
        return priceUnit;
    }
    
    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
    public String getCallDuration() {
        return callDuration;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getDirection() {
        return direction;
    }
    
}