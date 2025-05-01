package com.mind2web.vb360.apiservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mind2web.vb360.MyLifecycleHandler;
import com.mind2web.vb360.modeles.ChatConversationResponse;
import com.mind2web.vb360.modeles.EmailMessageTemplateResponse;
import com.mind2web.vb360.modeles.LoginResponse;
import com.mind2web.vb360.modeles.PhoneNumberSuggestionsResponse;
import com.mind2web.vb360.modeles.UserListResponse;
import com.mind2web.vb360.utils.UserSharedPreferences;
import com.mind2web.vb360.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {
    private static final String BASE_URL = "https://dev3.virtualbusiness360.com/api";
    public static final String TwilioBase_URL = "https://dev3.virtualbusiness360.com/";




    public LoginResponse login(String username, String password, String store_account_id, String deviceToken, String fcmToken) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username", username)
                    .addFormDataPart("password", password)
                    .addFormDataPart("store_account_id", store_account_id)
                    .addFormDataPart("device_token", deviceToken)
                    .addFormDataPart("fcm_token", fcmToken)
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/login")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, LoginResponse.class); // Assuming LoginResponse has a static method for parsing
            }else{
                assert response.body() != null;
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, LoginResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResponse forgotPassword(String email) {
        try {
            URL url = new URL(BASE_URL + "/forgetPassword");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String requestBody = "email=" + email;

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String responseString = response.toString();
                Gson gson = new Gson();
                return gson.fromJson(responseString, LoginResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
public Response markReadMessage(String messageId, String senderId) {
        try {
            OkHttpClient client = new OkHttpClient();
            // Define the URL
            String url = BASE_URL + "/conversation/update-read-status/" + senderId;

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("conv_ids", messageId)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return response;
            } else if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Response updateCallStatus() {
        try {
            OkHttpClient client = new OkHttpClient();
            // Define the URL
            String url = BASE_URL + "/set-active-call-status";

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("conv_ids", "")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return response;
            } else if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserListResponse getUserList(int page, int limit, String search) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(BASE_URL + "/user-list?limit=" + limit + "&page=" + page + "&search_user=" + search)
                    .addHeader("Cookie", "ci_session=d0p41l16jp08loe05l01gj3nb772d898; csrf_cookie_name=965f9df1f8a8e85961115e2491014ea3")
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return UserListResponse.fromJson(responseString);
            } else if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ChatConversationResponse getUserConversation(String userId) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(BASE_URL + "/user-conversation/" + userId)
                    .addHeader("Cookie", "ci_session=d0p41l16jp08loe05l01gj3nb772d898; csrf_cookie_name=965f9df1f8a8e85961115e2491014ea3")
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return ChatConversationResponse.fromJson(responseString); // Assuming ChatConversationResponse has a static method for parsing
            } else if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PhoneNumberSuggestionsResponse getPhoneNumbersSuggestions(String search) {
        try {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            Request request = new Request.Builder()
                    .url(BASE_URL + "/user/search?search=" + search + "&page=1&limit=5")
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();


            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, PhoneNumberSuggestionsResponse.class);
            } else if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmailMessageTemplateResponse getSmsEmailTemplate(String type) {
        try {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("is_template_type", type)
                    .build();
            Request request = new Request.Builder()
                    .url(BASE_URL + "/sms/get-list-email-template-detail")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();
            Response response = client.newCall(request).execute();


            if (response.isSuccessful()) {
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, EmailMessageTemplateResponse.class);
            } else if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response sendConversation(Context mContext, String message, String isSchedule, String userPhone, List<String> filePaths) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");

            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("phone_number", userPhone)
                    .addFormDataPart("fromPhoneNumber", UserSharedPreferences.getInstance(mContext).getStringPreferences(UserSharedPreferences.PREF_USER_PHONE_NUMBER))
                    .addFormDataPart("type", "custom")
                    .addFormDataPart("text_message", message)
                    .addFormDataPart("readyToSend", "")
                    .addFormDataPart("scheduled_datetime", isSchedule)
                    .addFormDataPart("NumSegment", "1")
                    .addFormDataPart("encoding", "GSM-7")
                    .addFormDataPart("mms_count", "");
            if (filePaths != null) {
                // Add files dynamically in a loop
                for (String filePath : filePaths) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        multipartBuilder.addFormDataPart("sms_media[]", file.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"), file));
                    }
                }

            }
            RequestBody body = multipartBuilder.build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/sms-send")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e("", responseString);
            if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response saveDraftMessage(Context mContext, String message, String userPhone, List<String> filePaths) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");

            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("phone_number", userPhone)
                    .addFormDataPart("fromPhoneNumber", UserSharedPreferences.getInstance(mContext).getStringPreferences(UserSharedPreferences.PREF_USER_PHONE_NUMBER))
                    .addFormDataPart("text_message", message)
                    .addFormDataPart("type", "1");
            if (filePaths != null) {
                // Add files dynamically in a loop
                for (String filePath : filePaths) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        multipartBuilder.addFormDataPart("sms_media[]", file.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"), file));
                    }
                }

            }
            RequestBody body = multipartBuilder.build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/sms-email-draft")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e("", responseString);
            if (response.code() == 401) {
                logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response sendEmail(Context mContext,String fromEmail, String recipient, String subject, String message, String isEmailSchedule, List<String> filePaths) {
        try {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("text/plain");

            // Build the MultipartBody
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("emailId", recipient)
                    .addFormDataPart("subject", subject)
                    .addFormDataPart("email_message", message)
                    .addFormDataPart("email", fromEmail)
                    .addFormDataPart("is_email_schedule", isEmailSchedule);


            if (filePaths != null) {
                // Add files dynamically in a loop
                for (String filePath : filePaths) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        multipartBuilder.addFormDataPart("attachment[]", file.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"), file));
                    }
                }

            }

            RequestBody body = multipartBuilder.build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/email/send-email")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.authToken)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                String res = response.body().string();
                System.out.println("Response: " + response.body().string());
                if (response.code() == 401) {
                    logout();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResponse logout() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("device_token", UserSharedPreferences.authToken)
                    .build();
            Request request = new Request.Builder()
                    .url(BASE_URL + "logout/employee-logout")
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                Gson gson = new Gson();
                Utils.logoutApp(MyLifecycleHandler.getCurrentActivity());
                return gson.fromJson(responseString, LoginResponse.class); // Assuming LoginResponse has a static method for parsing
            }
            Utils.logoutApp(MyLifecycleHandler.getCurrentActivity());
        } catch (Exception e) {
            e.printStackTrace();
            Utils.logoutApp(MyLifecycleHandler.getCurrentActivity());
        }
        return null;
    }
}




