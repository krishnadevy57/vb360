package com.mind2web.vb360;

// LoginActivity.java
import static android.app.Application.getProcessName;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.text.method.PasswordTransformationMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.modeles.LoginResponse;
import com.mind2web.vb360.utils.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {


    private EditText emailField, passwordField,storeAccountId;
    private Button signInButton;
//    private TextView forgotPassword;

    //    private ImageView passwordToggle;
//    private boolean isPasswordVisible = false;
String deviceToken;
String fcmTokn;
    LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        storeAccountId = findViewById(R.id.store_account_d);
         signInButton = findViewById(R.id.sign_in_button);
//        forgotPassword = findViewById(R.id.forgot_password);
//        FirebaseApp.initializeApp(this);
        String processName = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            processName = getProcessName();
        }
        String packageName = getPackageName();

        if (processName != null && processName.equals(packageName)) {
            FirebaseApp.initializeApp(this);
        }
        requestPermissions();

        // Toggle password visibility
//        passwordToggle.setOnClickListener(view -> {
//            isPasswordVisible = !isPasswordVisible;
//            if (isPasswordVisible) {
//                passwordEditText.setTransformationMethod(null);
//                passwordToggle.setImageResource(R.drawable.ic_visibility);
//            } else {
//                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
//                passwordToggle.setImageResource(R.drawable.ic_visibility_off);
//            }
//            passwordEditText.setSelection(passwordEditText.getText().length());
//        });

        // Forgot password functionality
//        forgotPassword.setOnClickListener(view -> {
//            Toast.makeText(LoginActivity.this, "Forgot Password clicked!", Toast.LENGTH_SHORT).show();
//        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get the token
                    String token = task.getResult();
                    Log.d("FCM", "Token: " + token);
                    deviceToken=token;
                    fcmTokn=token;
                    UserSharedPreferences.getInstance(LoginActivity.this).saveStringPreferences(UserSharedPreferences.ACTION_FCM_TOKEN,fcmTokn);
                });

        // Sign in functionality
        signInButton.setOnClickListener(view -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String store_account_id  = storeAccountId.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call your login function here
            login(email, password,store_account_id,deviceToken,fcmTokn);
        });

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
//                        return;
//                    }
//                    // Get the token
//                    String token = task.getResult();
//                    Log.d("FCM", "Token: " + token);
//                    deviceToken=token;
//                    fcmTokn=token;
//                    UserSharedPreferences.getInstance(LoginActivity.this).saveStringPreferences(UserSharedPreferences.ACTION_FCM_TOKEN,fcmTokn);
//                });
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(LoginActivity.this);
        if(userSharedPreferences.isLoggedIn){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.FOREGROUND_SERVICE_MICROPHONE,
                Manifest.permission.CALL_PHONE
        }, 100);
    }

    private void login(String email, String password,String store_account_id,String deviceToken,String fcmToken) {

        // Create an ExecutorService to run the login task in the background
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            LoginResponse loginResponse = apiService.login(email, password,store_account_id, deviceToken, fcmToken);

            runOnUiThread(() -> {
                if (loginResponse != null && "success".equalsIgnoreCase(loginResponse.getStatus())) {
                    // Successfully logged in, navigate to the main activity
                    UserSharedPreferences.getInstance(this).setUserPreferences(loginResponse.getUser().getUserId(), loginResponse.getAccessToken(), loginResponse.getUser().getTwilioCallingToken().getTwilioAccessToken(),  true,loginResponse.getUser().getEmail(),loginResponse.getUser().getPhone());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle login failure
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


}

