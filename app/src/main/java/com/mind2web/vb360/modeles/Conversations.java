package com.mind2web.vb360.modeles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Conversations {

   @SerializedName("id")
   String id;

   @SerializedName("sender_id")
   String senderId;

   @SerializedName("receiver_id")
   String receiverId;

   @SerializedName("type")
   String type;

   @SerializedName("unread_chat_users_read_status")
   String unread_chat_users_read_status;

   @SerializedName("type_id")
   String typeId;

   @SerializedName("read_status")
   String readStatus;

   @SerializedName("call_sid")
   String callSid;

   @SerializedName("created_at")
   String createdAt;

   @SerializedName("master_user_id")
   String masterUserId;

   @SerializedName("is_primary")
   String isPrimary;

   @SerializedName("is_master")
   String isMaster;

   @SerializedName("updated_at")
   String updatedAt;

   @SerializedName("userRefId")
   int userRefId;

   @SerializedName("toEmailAddress")
   String toEmailAddress;

   @SerializedName("ccEmailAddress")
   String ccEmailAddress;

   @SerializedName("bccEmailAddress")
   String bccEmailAddress;

   @SerializedName("replyToEmailAddress")
   String replyToEmailAddress;

   @SerializedName("replyToName")
   String replyToName;

   @SerializedName("fromEmailAddress")
   String fromEmailAddress;

   @SerializedName("fromName")
   String fromName;


   @SerializedName("queued_email_id")
   String queuedEmailId;

   @SerializedName("subject")
   String subject;

   @SerializedName("message")
   String message;

   @SerializedName("template_id")
   int templateId;

   @SerializedName("template_slug")
   String templateSlug;

   @SerializedName("alt_message")
   String altMessage;

   @SerializedName("queuedTime")
   String queuedTime;

   @SerializedName("sendDateTime")
   String sendDateTime;

   @SerializedName("readyForProcess")
   String readyForProcess;

   @SerializedName("processedTime")
   String processedTime;

   @SerializedName("processSuccess")
   String processSuccess;

   @SerializedName("recCreated")
   String recCreated;

   @SerializedName("recUpdated")
   String recUpdated;

   @SerializedName("recMarkedForDelete")
   String recMarkedForDelete;

   @SerializedName("user_id")
   String userId;

   @SerializedName("deleted")
   String deleted;

   @SerializedName("customer_id")
   String customerId;

   @SerializedName("transfer_type")
   String transferType;

   @SerializedName("udate")
   String udate;

   @SerializedName("message_id")
   String messageId;

   @SerializedName("in_reply_to")
   String inReplyTo;

   @SerializedName("automation_type")
   String automationType;

   @SerializedName("is_parent")
   String isParent;

   @SerializedName("parent_message_id")
   String parentMessageId;

   @SerializedName("queued_sms_id")
   String queuedSmsId;

   @SerializedName("toPhoneNumber")
   String toPhoneNumber;

   @SerializedName("fromPhoneNumber")
   String fromPhoneNumber;

   @SerializedName("country_code")
   String countryCode;

   @SerializedName("direction")
   String direction;

   @SerializedName("is_read")
   String isRead;

   @SerializedName("NumSegment")
   String NumSegment;

   @SerializedName("readyToSend")
   String readyToSend;

   @SerializedName("processStatus")
   String processStatus;

   @SerializedName("encoding")
   String encoding;

   @SerializedName("scheduled_datetime")
   String scheduledDatetime;

   @SerializedName("sms_sid")
   String smsSid;

   @SerializedName("sms_status")
   String smsStatus;

   @SerializedName("mms_count")
   String mmsCount;

   @SerializedName("call_by")
   String callBy;

   @SerializedName("call_to")
   String callTo;

   @SerializedName("identity")
   String identity;

   @SerializedName("call_status")
   String callStatus;

   @SerializedName("parent_call_sid")
   String parentCallSid;

   @SerializedName("call_duration")
   String callDuration;

   @SerializedName("call_cost")
   String callCost;

   @SerializedName("first_name")
   String firstName;

   @SerializedName("last_name")
   String lastName;

   @SerializedName("price_unit")
   String priceUnit;

   @SerializedName("call_start_time")
   String callStartTime;

   @SerializedName("call_end_time")
   String callEndTime;

   @SerializedName("timezone")
   String timezone;

   @SerializedName("created_by")
   String createdBy;

   @SerializedName("call_by_user_id")
   String callByUserId;

   @SerializedName("call_to_user_id")
   String callToUserId;

   @SerializedName("round_of_call_duration")
   String roundOfCallDuration;

   @SerializedName("notes")
   String notes;

   @SerializedName("child_sid")
   String childSid;

   @SerializedName("virtual_receptionist_number")
   String virtualReceptionistNumber;

   @SerializedName("real_call_direction")
   String realCallDirection;

   @SerializedName("is_call_transfer")
   String isCallTransfer;

   @SerializedName("call_by_transfer")
   String callByTransfer;

   @SerializedName("call_to_transfer")
   String callToTransfer;

   @SerializedName("call_transfer_to_other_employee")
   String callTransferToOtherEmployee;

   @SerializedName("call_transfer_id")
   String callTransferId;

   @SerializedName("virtual_receptionist_associate_id")
   String virtualReceptionistAssociateId;

   @SerializedName("is_call_ring_to_all")
   String isCallRingToAll;

   @SerializedName("is_call_ring_response")
   String isCallRingResponse;

   @SerializedName("original_call_to_user_id")
   String originalCallToUserId;

   @SerializedName("call_detail_sid")
   String callDetailSid;

   @SerializedName("account_sid")
   String accountSid;

   @SerializedName("api_version")
   Date apiVersion;

   @SerializedName("called")
   String called;

   @SerializedName("called_city")
   String calledCity;

   @SerializedName("called_country")
   String calledCountry;

   @SerializedName("called_state")
   String calledState;

   @SerializedName("called_zip")
   String calledZip;

   @SerializedName("caller")
   String caller;

   @SerializedName("caller_city")
   String callerCity;

   @SerializedName("caller_country")
   String callerCountry;

   @SerializedName("caller_state")
   String callerState;

   @SerializedName("caller_zip")
   String callerZip;

   @SerializedName("from_city")
   String fromCity;

   @SerializedName("from_country")
   String fromCountry;

   @SerializedName("from_state")
   String fromState;

   @SerializedName("from_zip")
   String fromZip;

   @SerializedName("recording_duration")
   String recordingDuration;

   @SerializedName("recording_sid")
   String recordingSid;

   @SerializedName("recording_url")
   String recordingUrl;

   @SerializedName("to_city")
   String toCity;

   @SerializedName("to_country")
   String toCountry;

   @SerializedName("to_state")
   String toState;

   @SerializedName("to_zip")
   String toZip;

   @SerializedName("voice_message")
   String voiceMessage;

   @SerializedName("round_of_recording_duration")
   String roundOfRecordingDuration;

   @SerializedName("call_error_code")
   String callErrorCode;

   @SerializedName("call_status_text")
   String callStatusText;

   @SerializedName("call_notification_sid")
   String callNotificationSid;

   @SerializedName("call_error_more_info")
   String callErrorMoreInfo;

   @SerializedName("osr_id")
   String osrId;

   @SerializedName("username")
   String username;

   @SerializedName("wholesaler")
   String wholesaler;

   @SerializedName("lost_password_email")
   String lostPasswordEmail;

   @SerializedName("shipping_id")
   String shippingId;

   @SerializedName("billing_id")
   String billingId;

   @SerializedName("last_login")
   String lastLogin;

   @SerializedName("current_login")
   String currentLogin;

   @SerializedName("admin")
   String admin;

   @SerializedName("no_tax")
   String noTax;

   @SerializedName("temp_code")
   String tempCode;

   @SerializedName("mark_up")
   String markUp;

   @SerializedName("full_price_shipping")
   String fullPriceShipping;

   @SerializedName("user_type")
   String userType;

   @SerializedName("source")
   String source;

   @SerializedName("employee_type")
   String employeeType;

   @SerializedName("in_round_robin")
   String inRoundRobin;

   @SerializedName("ran_round_robin_at")
   String ranRoundRobinAt;

   @SerializedName("status")
   String status;

   @SerializedName("lead_status")
   String leadStatus;

   @SerializedName("list_per_page")
   String listPerPage;

   @SerializedName("cc_permission")
   String ccPermission;

   @SerializedName("amount")
   String amount;

   @SerializedName("does_deliveries")
   String doesDeliveries;

   @SerializedName("last_customer_seen")
   String lastCustomerSeen;

   @SerializedName("register_date")
   String registerDate;

   @SerializedName("profile_image")
   String profileImage;

   @SerializedName("creation_source")
   String creationSource;

   @SerializedName("merge_customer_notice")
   String mergeCustomerNotice;

   @SerializedName("merge_customer_notice_count")
   String mergeCustomerNoticeCount;

   @SerializedName("auto_created_at")
   String autoCreatedAt;

   @SerializedName("auto_updated_at")
   String autoUpdatedAt;

   @SerializedName("street_address")
   String streetAddress;

   @SerializedName("address_2")
   String address2;

   @SerializedName("city")
   String city;

   @SerializedName("state")
   String state;

   @SerializedName("zip")
   String zip;

   @SerializedName("country")
   String country;

   @SerializedName("email")
   String email;

   @SerializedName("phone")
   String phone;

   @SerializedName("company")
   String company;

   @SerializedName("all_states_country")
   String allStatesCountry;

   @SerializedName("sales_email")
   String salesEmail;

   @SerializedName("website")
   String website;

   @SerializedName("additional_phone")
   String additionalPhone;

   @SerializedName("additional_url")
   String additionalUrl;

   @SerializedName("additional_company")
   String additionalCompany;

   @SerializedName("store_currency")
   String storeCurrency;

   @SerializedName("designation")
   String designation;

   @SerializedName("employee_id")
   String employeeId;

   @SerializedName("conversation_id")
   String conversationId;

   @SerializedName("mail_list_queued_email_id")
   String mailListQueuedEmailId;

   @SerializedName("voice_call_status")
   String voiceCallStatus;

   @SerializedName("call_detail_parent_call_sid")
   String callDetailParentCallSid;

   @SerializedName("failed_call_status")
   String failedCallStatus;



    @SerializedName("call_recording")
    public ArrayList<String> call_recording;

   @SerializedName("conv_id")
   String convId;

   @SerializedName("from_detail")
   String fromDetail;

   @SerializedName("to_detail")
   String toDetail;

   @SerializedName("updated_chat_time")
   String updatedChatTime;

   @SerializedName("call_status_label")
   String callStatusLabel;

   @SerializedName("sent_status")
   String sentStatus;

   @SerializedName("parent_call_details")
   ParentCallDetails parentCallDetails;

   @SerializedName("escaped_details")
   String escapedDetails;

   @SerializedName("custom_user_name")
   String customUserName;

   @SerializedName("user_short_form")
   String userShortForm;

   @SerializedName("chat_time")
   String chatTime;

   @SerializedName("email_attachment")
   List<String> emailAttachment;

   @SerializedName("sms_attachment")
   List<String> smsAttachment;

   @SerializedName("custom_sender_name")
   String customSenderName;

   @SerializedName("sender_short_form")
   String senderShortForm;

    public String getUnread_chat_users_read_status() {
        return unread_chat_users_read_status;
    }

    public void setUnread_chat_users_read_status(String unread_chat_users_read_status) {
        this.unread_chat_users_read_status = unread_chat_users_read_status;
    }

    public ArrayList<String> getCall_recording() {
        return call_recording;
    }

    public void setCall_recording(ArrayList<String> call_recording) {
        this.call_recording = call_recording;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public String getSenderId() {
        return senderId;
    }
    
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    public String getReceiverId() {
        return receiverId;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getTypeId() {
        return typeId;
    }
    
    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
    public String getReadStatus() {
        return readStatus;
    }
    
    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }
    public String getCallSid() {
        return callSid;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setMasterUserId(String masterUserId) {
        this.masterUserId = masterUserId;
    }
    public String getMasterUserId() {
        return masterUserId;
    }
    
    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }
    public String getIsPrimary() {
        return isPrimary;
    }
    
    public void setIsMaster(String isMaster) {
        this.isMaster = isMaster;
    }
    public String getIsMaster() {
        return isMaster;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUserRefId(int userRefId) {
        this.userRefId = userRefId;
    }
    public int getUserRefId() {
        return userRefId;
    }
    
    public void setToEmailAddress(String toEmailAddress) {
        this.toEmailAddress = toEmailAddress;
    }
    public String getToEmailAddress() {
        return toEmailAddress;
    }
    
    public void setCcEmailAddress(String ccEmailAddress) {
        this.ccEmailAddress = ccEmailAddress;
    }
    public String getCcEmailAddress() {
        return ccEmailAddress;
    }
    
    public void setBccEmailAddress(String bccEmailAddress) {
        this.bccEmailAddress = bccEmailAddress;
    }
    public String getBccEmailAddress() {
        return bccEmailAddress;
    }
    
    public void setReplyToEmailAddress(String replyToEmailAddress) {
        this.replyToEmailAddress = replyToEmailAddress;
    }
    public String getReplyToEmailAddress() {
        return replyToEmailAddress;
    }
    
    public void setReplyToName(String replyToName) {
        this.replyToName = replyToName;
    }
    public String getReplyToName() {
        return replyToName;
    }
    
    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }
    public String getFromEmailAddress() {
        return fromEmailAddress;
    }
    
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public String getFromName() {
        return fromName;
    }

    public void setQueuedEmailId(String queuedEmailId) {
        this.queuedEmailId = queuedEmailId;
    }
    public String getQueuedEmailId() {
        return queuedEmailId;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject() {
        return subject;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    
    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public int getTemplateId() {
        return templateId;
    }
    
    public void setTemplateSlug(String templateSlug) {
        this.templateSlug = templateSlug;
    }
    public String getTemplateSlug() {
        return templateSlug;
    }
    
    public void setAltMessage(String altMessage) {
        this.altMessage = altMessage;
    }
    public String getAltMessage() {
        return altMessage;
    }
    
    public void setQueuedTime(String queuedTime) {
        this.queuedTime = queuedTime;
    }
    public String getQueuedTime() {
        return queuedTime;
    }
    
    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }
    public String getSendDateTime() {
        return sendDateTime;
    }
    
    public void setReadyForProcess(String readyForProcess) {
        this.readyForProcess = readyForProcess;
    }
    public String getReadyForProcess() {
        return readyForProcess;
    }
    
    public void setProcessedTime(String processedTime) {
        this.processedTime = processedTime;
    }
    public String getProcessedTime() {
        return processedTime;
    }
    
    public void setProcessSuccess(String processSuccess) {
        this.processSuccess = processSuccess;
    }
    public String getProcessSuccess() {
        return processSuccess;
    }
    
    public void setRecCreated(String recCreated) {
        this.recCreated = recCreated;
    }
    public String getRecCreated() {
        return recCreated;
    }
    
    public void setRecUpdated(String recUpdated) {
        this.recUpdated = recUpdated;
    }
    public String getRecUpdated() {
        return recUpdated;
    }
    
    public void setRecMarkedForDelete(String recMarkedForDelete) {
        this.recMarkedForDelete = recMarkedForDelete;
    }
    public String getRecMarkedForDelete() {
        return recMarkedForDelete;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
    public String getDeleted() {
        return deleted;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerId() {
        return customerId;
    }
    
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
    public String getTransferType() {
        return transferType;
    }
    
    public void setUdate(String udate) {
        this.udate = udate;
    }
    public String getUdate() {
        return udate;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getMessageId() {
        return messageId;
    }
    
    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }
    public String getInReplyTo() {
        return inReplyTo;
    }
    
    public void setAutomationType(String automationType) {
        this.automationType = automationType;
    }
    public String getAutomationType() {
        return automationType;
    }
    
    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
    public String getIsParent() {
        return isParent;
    }
    
    public void setParentMessageId(String parentMessageId) {
        this.parentMessageId = parentMessageId;
    }
    public String getParentMessageId() {
        return parentMessageId;
    }
    
    public void setQueuedSmsId(String queuedSmsId) {
        this.queuedSmsId = queuedSmsId;
    }
    public String getQueuedSmsId() {
        return queuedSmsId;
    }
    
    public void setToPhoneNumber(String toPhoneNumber) {
        this.toPhoneNumber = toPhoneNumber;
    }
    public String getToPhoneNumber() {
        return toPhoneNumber;
    }
    
    public void setFromPhoneNumber(String fromPhoneNumber) {
        this.fromPhoneNumber = fromPhoneNumber;
    }
    public String getFromPhoneNumber() {
        return fromPhoneNumber;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getDirection() {
        return direction;
    }
    
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
    public String getIsRead() {
        return isRead;
    }
    
    public void setNumSegment(String NumSegment) {
        this.NumSegment = NumSegment;
    }
    public String getNumSegment() {
        return NumSegment;
    }
    
    public void setReadyToSend(String readyToSend) {
        this.readyToSend = readyToSend;
    }
    public String getReadyToSend() {
        return readyToSend;
    }
    
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
    public String getProcessStatus() {
        return processStatus;
    }
    
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    public String getEncoding() {
        return encoding;
    }
    
    public void setScheduledDatetime(String scheduledDatetime) {
        this.scheduledDatetime = scheduledDatetime;
    }
    public String getScheduledDatetime() {
        return scheduledDatetime;
    }
    
    public void setSmsSid(String smsSid) {
        this.smsSid = smsSid;
    }
    public String getSmsSid() {
        return smsSid;
    }
    
    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }
    public String getSmsStatus() {
        return smsStatus;
    }
    
    public void setMmsCount(String mmsCount) {
        this.mmsCount = mmsCount;
    }
    public String getMmsCount() {
        return mmsCount;
    }
    
    public void setCallBy(String callBy) {
        this.callBy = callBy;
    }
    public String getCallBy() {
        return callBy;
    }
    
    public void setCallTo(String callTo) {
        this.callTo = callTo;
    }
    public String getCallTo() {
        return callTo;
    }
    
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public String getIdentity() {
        return identity;
    }
    
    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }
    public String getCallStatus() {
        return callStatus;
    }
    
    public void setParentCallSid(String parentCallSid) {
        this.parentCallSid = parentCallSid;
    }
    public String getParentCallSid() {
        return parentCallSid;
    }
    
    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
    public String getCallDuration() {
        return callDuration;
    }
    
    public void setCallCost(String callCost) {
        this.callCost = callCost;
    }
    public String getCallCost() {
        return callCost;
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
    
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }
    public String getPriceUnit() {
        return priceUnit;
    }
    
    public void setCallStartTime(String callStartTime) {
        this.callStartTime = callStartTime;
    }
    public String getCallStartTime() {
        return callStartTime;
    }
    
    public void setCallEndTime(String callEndTime) {
        this.callEndTime = callEndTime;
    }
    public String getCallEndTime() {
        return callEndTime;
    }
    
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    public String getTimezone() {
        return timezone;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCallByUserId(String callByUserId) {
        this.callByUserId = callByUserId;
    }
    public String getCallByUserId() {
        return callByUserId;
    }
    
    public void setCallToUserId(String callToUserId) {
        this.callToUserId = callToUserId;
    }
    public String getCallToUserId() {
        return callToUserId;
    }
    
    public void setRoundOfCallDuration(String roundOfCallDuration) {
        this.roundOfCallDuration = roundOfCallDuration;
    }
    public String getRoundOfCallDuration() {
        return roundOfCallDuration;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes() {
        return notes;
    }
    
    public void setChildSid(String childSid) {
        this.childSid = childSid;
    }
    public String getChildSid() {
        return childSid;
    }
    
    public void setVirtualReceptionistNumber(String virtualReceptionistNumber) {
        this.virtualReceptionistNumber = virtualReceptionistNumber;
    }
    public String getVirtualReceptionistNumber() {
        return virtualReceptionistNumber;
    }
    
    public void setRealCallDirection(String realCallDirection) {
        this.realCallDirection = realCallDirection;
    }
    public String getRealCallDirection() {
        return realCallDirection;
    }
    
    public void setIsCallTransfer(String isCallTransfer) {
        this.isCallTransfer = isCallTransfer;
    }
    public String getIsCallTransfer() {
        return isCallTransfer;
    }
    
    public void setCallByTransfer(String callByTransfer) {
        this.callByTransfer = callByTransfer;
    }
    public String getCallByTransfer() {
        return callByTransfer;
    }
    
    public void setCallToTransfer(String callToTransfer) {
        this.callToTransfer = callToTransfer;
    }
    public String getCallToTransfer() {
        return callToTransfer;
    }
    
    public void setCallTransferToOtherEmployee(String callTransferToOtherEmployee) {
        this.callTransferToOtherEmployee = callTransferToOtherEmployee;
    }
    public String getCallTransferToOtherEmployee() {
        return callTransferToOtherEmployee;
    }
    
    public void setCallTransferId(String callTransferId) {
        this.callTransferId = callTransferId;
    }
    public String getCallTransferId() {
        return callTransferId;
    }
    
    public void setVirtualReceptionistAssociateId(String virtualReceptionistAssociateId) {
        this.virtualReceptionistAssociateId = virtualReceptionistAssociateId;
    }
    public String getVirtualReceptionistAssociateId() {
        return virtualReceptionistAssociateId;
    }
    
    public void setIsCallRingToAll(String isCallRingToAll) {
        this.isCallRingToAll = isCallRingToAll;
    }
    public String getIsCallRingToAll() {
        return isCallRingToAll;
    }
    
    public void setIsCallRingResponse(String isCallRingResponse) {
        this.isCallRingResponse = isCallRingResponse;
    }
    public String getIsCallRingResponse() {
        return isCallRingResponse;
    }
    
    public void setOriginalCallToUserId(String originalCallToUserId) {
        this.originalCallToUserId = originalCallToUserId;
    }
    public String getOriginalCallToUserId() {
        return originalCallToUserId;
    }
    
    public void setCallDetailSid(String callDetailSid) {
        this.callDetailSid = callDetailSid;
    }
    public String getCallDetailSid() {
        return callDetailSid;
    }
    
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
    public String getAccountSid() {
        return accountSid;
    }
    
    public void setApiVersion(Date apiVersion) {
        this.apiVersion = apiVersion;
    }
    public Date getApiVersion() {
        return apiVersion;
    }
    
    public void setCalled(String called) {
        this.called = called;
    }
    public String getCalled() {
        return called;
    }
    
    public void setCalledCity(String calledCity) {
        this.calledCity = calledCity;
    }
    public String getCalledCity() {
        return calledCity;
    }
    
    public void setCalledCountry(String calledCountry) {
        this.calledCountry = calledCountry;
    }
    public String getCalledCountry() {
        return calledCountry;
    }
    
    public void setCalledState(String calledState) {
        this.calledState = calledState;
    }
    public String getCalledState() {
        return calledState;
    }
    
    public void setCalledZip(String calledZip) {
        this.calledZip = calledZip;
    }
    public String getCalledZip() {
        return calledZip;
    }
    
    public void setCaller(String caller) {
        this.caller = caller;
    }
    public String getCaller() {
        return caller;
    }
    
    public void setCallerCity(String callerCity) {
        this.callerCity = callerCity;
    }
    public String getCallerCity() {
        return callerCity;
    }
    
    public void setCallerCountry(String callerCountry) {
        this.callerCountry = callerCountry;
    }
    public String getCallerCountry() {
        return callerCountry;
    }
    
    public void setCallerState(String callerState) {
        this.callerState = callerState;
    }
    public String getCallerState() {
        return callerState;
    }
    
    public void setCallerZip(String callerZip) {
        this.callerZip = callerZip;
    }
    public String getCallerZip() {
        return callerZip;
    }
    
    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }
    public String getFromCity() {
        return fromCity;
    }
    
    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }
    public String getFromCountry() {
        return fromCountry;
    }
    
    public void setFromState(String fromState) {
        this.fromState = fromState;
    }
    public String getFromState() {
        return fromState;
    }
    
    public void setFromZip(String fromZip) {
        this.fromZip = fromZip;
    }
    public String getFromZip() {
        return fromZip;
    }
    
    public void setRecordingDuration(String recordingDuration) {
        this.recordingDuration = recordingDuration;
    }
    public String getRecordingDuration() {
        return recordingDuration;
    }
    
    public void setRecordingSid(String recordingSid) {
        this.recordingSid = recordingSid;
    }
    public String getRecordingSid() {
        return recordingSid;
    }
    
    public void setRecordingUrl(String recordingUrl) {
        this.recordingUrl = recordingUrl;
    }
    public String getRecordingUrl() {
        return recordingUrl;
    }
    
    public void setToCity(String toCity) {
        this.toCity = toCity;
    }
    public String getToCity() {
        return toCity;
    }
    
    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }
    public String getToCountry() {
        return toCountry;
    }
    
    public void setToState(String toState) {
        this.toState = toState;
    }
    public String getToState() {
        return toState;
    }
    
    public void setToZip(String toZip) {
        this.toZip = toZip;
    }
    public String getToZip() {
        return toZip;
    }
    
    public void setVoiceMessage(String voiceMessage) {
        this.voiceMessage = voiceMessage;
    }
    public String getVoiceMessage() {
        return voiceMessage;
    }
    
    public void setRoundOfRecordingDuration(String roundOfRecordingDuration) {
        this.roundOfRecordingDuration = roundOfRecordingDuration;
    }
    public String getRoundOfRecordingDuration() {
        return roundOfRecordingDuration;
    }
    
    public void setCallErrorCode(String callErrorCode) {
        this.callErrorCode = callErrorCode;
    }
    public String getCallErrorCode() {
        return callErrorCode;
    }
    
    public void setCallStatusText(String callStatusText) {
        this.callStatusText = callStatusText;
    }
    public String getCallStatusText() {
        return callStatusText;
    }
    
    public void setCallNotificationSid(String callNotificationSid) {
        this.callNotificationSid = callNotificationSid;
    }
    public String getCallNotificationSid() {
        return callNotificationSid;
    }
    
    public void setCallErrorMoreInfo(String callErrorMoreInfo) {
        this.callErrorMoreInfo = callErrorMoreInfo;
    }
    public String getCallErrorMoreInfo() {
        return callErrorMoreInfo;
    }
    
    public void setOsrId(String osrId) {
        this.osrId = osrId;
    }
    public String getOsrId() {
        return osrId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    
    public void setWholesaler(String wholesaler) {
        this.wholesaler = wholesaler;
    }
    public String getWholesaler() {
        return wholesaler;
    }
    
    public void setLostPasswordEmail(String lostPasswordEmail) {
        this.lostPasswordEmail = lostPasswordEmail;
    }
    public String getLostPasswordEmail() {
        return lostPasswordEmail;
    }
    
    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }
    public String getShippingId() {
        return shippingId;
    }
    
    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }
    public String getBillingId() {
        return billingId;
    }
    
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
    public String getLastLogin() {
        return lastLogin;
    }
    
    public void setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
    }
    public String getCurrentLogin() {
        return currentLogin;
    }
    
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public String getAdmin() {
        return admin;
    }
    
    public void setNoTax(String noTax) {
        this.noTax = noTax;
    }
    public String getNoTax() {
        return noTax;
    }
    
    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }
    public String getTempCode() {
        return tempCode;
    }
    
    public void setMarkUp(String markUp) {
        this.markUp = markUp;
    }
    public String getMarkUp() {
        return markUp;
    }
    
    public void setFullPriceShipping(String fullPriceShipping) {
        this.fullPriceShipping = fullPriceShipping;
    }
    public String getFullPriceShipping() {
        return fullPriceShipping;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType() {
        return userType;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }
    
    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
    public String getEmployeeType() {
        return employeeType;
    }
    
    public void setInRoundRobin(String inRoundRobin) {
        this.inRoundRobin = inRoundRobin;
    }
    public String getInRoundRobin() {
        return inRoundRobin;
    }
    
    public void setRanRoundRobinAt(String ranRoundRobinAt) {
        this.ranRoundRobinAt = ranRoundRobinAt;
    }
    public String getRanRoundRobinAt() {
        return ranRoundRobinAt;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    
    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }
    public String getLeadStatus() {
        return leadStatus;
    }
    
    public void setListPerPage(String listPerPage) {
        this.listPerPage = listPerPage;
    }
    public String getListPerPage() {
        return listPerPage;
    }
    
    public void setCcPermission(String ccPermission) {
        this.ccPermission = ccPermission;
    }
    public String getCcPermission() {
        return ccPermission;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }
    
    public void setDoesDeliveries(String doesDeliveries) {
        this.doesDeliveries = doesDeliveries;
    }
    public String getDoesDeliveries() {
        return doesDeliveries;
    }
    
    public void setLastCustomerSeen(String lastCustomerSeen) {
        this.lastCustomerSeen = lastCustomerSeen;
    }
    public String getLastCustomerSeen() {
        return lastCustomerSeen;
    }
    
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
    public String getRegisterDate() {
        return registerDate;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setCreationSource(String creationSource) {
        this.creationSource = creationSource;
    }
    public String getCreationSource() {
        return creationSource;
    }
    
    public void setMergeCustomerNotice(String mergeCustomerNotice) {
        this.mergeCustomerNotice = mergeCustomerNotice;
    }
    public String getMergeCustomerNotice() {
        return mergeCustomerNotice;
    }
    
    public void setMergeCustomerNoticeCount(String mergeCustomerNoticeCount) {
        this.mergeCustomerNoticeCount = mergeCustomerNoticeCount;
    }
    public String getMergeCustomerNoticeCount() {
        return mergeCustomerNoticeCount;
    }
    
    public void setAutoCreatedAt(String autoCreatedAt) {
        this.autoCreatedAt = autoCreatedAt;
    }
    public String getAutoCreatedAt() {
        return autoCreatedAt;
    }
    
    public void setAutoUpdatedAt(String autoUpdatedAt) {
        this.autoUpdatedAt = autoUpdatedAt;
    }
    public String getAutoUpdatedAt() {
        return autoUpdatedAt;
    }
    
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getAddress2() {
        return address2;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getZip() {
        return zip;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }
    
    public void setAllStatesCountry(String allStatesCountry) {
        this.allStatesCountry = allStatesCountry;
    }
    public String getAllStatesCountry() {
        return allStatesCountry;
    }
    
    public void setSalesEmail(String salesEmail) {
        this.salesEmail = salesEmail;
    }
    public String getSalesEmail() {
        return salesEmail;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getWebsite() {
        return website;
    }
    
    public void setAdditionalPhone(String additionalPhone) {
        this.additionalPhone = additionalPhone;
    }
    public String getAdditionalPhone() {
        return additionalPhone;
    }
    
    public void setAdditionalUrl(String additionalUrl) {
        this.additionalUrl = additionalUrl;
    }
    public String getAdditionalUrl() {
        return additionalUrl;
    }
    
    public void setAdditionalCompany(String additionalCompany) {
        this.additionalCompany = additionalCompany;
    }
    public String getAdditionalCompany() {
        return additionalCompany;
    }
    
    public void setStoreCurrency(String storeCurrency) {
        this.storeCurrency = storeCurrency;
    }
    public String getStoreCurrency() {
        return storeCurrency;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getDesignation() {
        return designation;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
    public String getConversationId() {
        return conversationId;
    }
    
    public void setMailListQueuedEmailId(String mailListQueuedEmailId) {
        this.mailListQueuedEmailId = mailListQueuedEmailId;
    }
    public String getMailListQueuedEmailId() {
        return mailListQueuedEmailId;
    }
    
    public void setVoiceCallStatus(String voiceCallStatus) {
        this.voiceCallStatus = voiceCallStatus;
    }
    public String getVoiceCallStatus() {
        return voiceCallStatus;
    }
    
    public void setCallDetailParentCallSid(String callDetailParentCallSid) {
        this.callDetailParentCallSid = callDetailParentCallSid;
    }
    public String getCallDetailParentCallSid() {
        return callDetailParentCallSid;
    }
    
    public void setFailedCallStatus(String failedCallStatus) {
        this.failedCallStatus = failedCallStatus;
    }
    public String getFailedCallStatus() {
        return failedCallStatus;
    }
    
    public void setConvId(String convId) {
        this.convId = convId;
    }
    public String getConvId() {
        return convId;
    }
    
    public void setFromDetail(String fromDetail) {
        this.fromDetail = fromDetail;
    }
    public String getFromDetail() {
        return fromDetail;
    }
    
    public void setToDetail(String toDetail) {
        this.toDetail = toDetail;
    }
    public String getToDetail() {
        return toDetail;
    }
    
    public void setUpdatedChatTime(String updatedChatTime) {
        this.updatedChatTime = updatedChatTime;
    }
    public String getUpdatedChatTime() {
        return updatedChatTime;
    }
    
    public void setCallStatusLabel(String callStatusLabel) {
        this.callStatusLabel = callStatusLabel;
    }
    public String getCallStatusLabel() {
        return callStatusLabel;
    }
    
    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }
    public String getSentStatus() {
        return sentStatus;
    }
    
    public void setParentCallDetails(ParentCallDetails parentCallDetails) {
        this.parentCallDetails = parentCallDetails;
    }
    public ParentCallDetails getParentCallDetails() {
        return parentCallDetails;
    }
    
    public void setEscapedDetails(String escapedDetails) {
        this.escapedDetails = escapedDetails;
    }
    public String getEscapedDetails() {
        return escapedDetails;
    }
    
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
    
    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }
    public String getChatTime() {
        return chatTime;
    }
    
    public void setEmailAttachment(List<String> emailAttachment) {
        this.emailAttachment = emailAttachment;
    }
    public List<String> getEmailAttachment() {
        return emailAttachment;
    }
    
    public void setCustomSenderName(String customSenderName) {
        this.customSenderName = customSenderName;
    }
    public String getCustomSenderName() {
        return customSenderName;
    }
    
    public void setSenderShortForm(String senderShortForm) {
        this.senderShortForm = senderShortForm;
    }
    public String getSenderShortForm() {
        return senderShortForm;
    }


    public List<String> getSmsAttachment() {
        return smsAttachment;
    }

    public void setSmsAttachment(List<String> smsAttachment) {
        this.smsAttachment = smsAttachment;
    }
}