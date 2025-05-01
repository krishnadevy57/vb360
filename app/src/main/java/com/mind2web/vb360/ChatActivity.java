package com.mind2web.vb360;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.mind2web.vb360.adapters.ChatAdapter;
import com.mind2web.vb360.adapters.EmailTemplateAdapter;
import com.mind2web.vb360.adapters.ImageListAdapter;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.databinding.ActivityChatBinding;
import com.mind2web.vb360.databinding.ActivityEmailBinding;
import com.mind2web.vb360.modeles.ChatConversationResponse;
import com.mind2web.vb360.modeles.Conversations;
import com.mind2web.vb360.modeles.EmailMessageTemplateData;
import com.mind2web.vb360.modeles.EmailMessageTemplateResponse;
import com.mind2web.vb360.utils.UserSharedPreferences;
import com.mind2web.vb360.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    boolean isLoading = false;
    private ChatAdapter chatAdapter;
    private List<Conversations> messages = new ArrayList<>();
    //    private Toolbar toolbar;
    String userPhone, userEmail;
    ActivityChatBinding binding;
    EmailTemplateAdapter templateListAdapter;
    private List<EmailMessageTemplateData> messageTemplate = new ArrayList<EmailMessageTemplateData>();
    private List<Uri> selectedImages = new ArrayList<>();
    ImageListAdapter imageAdapter;
    private ActivityResultLauncher<Intent> filePickerLauncher;

    private ActivityResultLauncher<Intent> activityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

// Change navigation icon color
        Drawable navIcon = toolbar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }

        userPhone = getIntent().getStringExtra("userphone");
        userEmail = getIntent().getStringExtra("useremail");
        TabLayout tabLayout = findViewById(R.id.tabLayout1);
        TextView tvShortNumb = findViewById(R.id.tvShortNumb);
        ImageButton btn_call = findViewById(R.id.btn_call);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userPhone.isEmpty()){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    executorService.execute(() -> {
                        ApiService apiService = new ApiService();
                        apiService.updateCallStatus();
                    });
                Intent intent = new Intent(ChatActivity.this, VoiceActivity.class);
                intent.putExtra("phone",userPhone);
                    activityLauncher.launch(intent);
                }
            }
        });

        // Set the icon on the tab at index 0 (Message Tab)
        TabLayout.Tab tab = tabLayout.getTabAt(0);
//        if (tab != null) {
//            tab.setIcon(R.drawable.message); // Replace with your actual drawable
//        }
//        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
//        if (tab1 != null) {
//            tab1.setIcon(R.drawable.email); // Replace with your actual drawable
//        }
        ImageView expendBtn = findViewById(R.id.expendBtn);

        binding.loader.setVisibility(View.GONE);
        binding.recipientField.setVisibility(View.GONE);

        // Set Horizontal Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.imageRecycleList.setLayoutManager(layoutManager);

        imageAdapter = new ImageListAdapter(this, selectedImages, this::deleteImage);
        binding.imageRecycleList.setAdapter(imageAdapter);
        setHightImageRecycle();


        binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
        // Register the ActivityResultLauncher
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        handleSelectedFiles(result.getData());
                    }
                }
        );
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    binding.recipientField.setVisibility(View.GONE);
                    getTemplates("1");
                } else {
                    binding.recipientField.setVisibility(View.VISIBLE);
                    getTemplates("0");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    binding.recipientField.setVisibility(View.GONE);
                } else {
                    binding.recipientField.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    binding.recipientField.setVisibility(View.GONE);
                } else {
                    binding.recipientField.setVisibility(View.VISIBLE);
                }
            }
        });

        // Handle image selection button click
        binding.selectImagesButton.setOnClickListener(view -> openFileChooser());
        expendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0) {

                    Intent intent = new Intent(ChatActivity.this, SendMessageActivity.class);
                    intent.putExtra("message", binding.messageInput.getHtml());
                    intent.putExtra("userphone", userPhone);
                    intent.putParcelableArrayListExtra("selected_images", new ArrayList<>(selectedImages));

                    startActivity(intent);

                } else {
                    Intent intent = new Intent(ChatActivity.this, EmailActivity.class);
                    intent.putExtra("message", binding.messageInput.getHtml());
                    intent.putExtra("subject", binding.recipientField.getText().toString());
                    intent.putExtra("useremail", userEmail);
                    intent.putParcelableArrayListExtra("selected_images", new ArrayList<>(selectedImages));
                    startActivity(intent);
                }
            }
        });
        String fullname = getIntent().getStringExtra("fullname");
        String shortname = getIntent().getStringExtra("shortname");



        if(fullname!=null&&(!fullname.equalsIgnoreCase(""))){
            tvShortNumb.setText(fullname);
        }else if(userPhone !=null&&(!userPhone.equalsIgnoreCase(""))){
            tvShortNumb.setText(userPhone);
        }else {
            tvShortNumb.setText(userEmail);

        }

//        binding.messageInput.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEND) {
//                // Handle the "Send" action
//                String message = binding.messageInput.getHtml().toString();
//                if (!message.isEmpty()) {
//                    // Perform the desired action, e.g., send the message
//                    if (tabLayout.getSelectedTabPosition() == 0) {
//                        sendMessage();
//                    } else {
//                        sendEmailMessage();
//                    }
//                }
//                return true;
//            }
//            return false;
//        });
        chatAdapter = new ChatAdapter(ChatActivity.this,messages);
        binding.recyclerView.setAdapter(chatAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            getUserConversation();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.sendButton.setEnabled(false); // Disable the button
                view.postDelayed(() -> binding.sendButton.setEnabled(true), 500); // Re-enable after 500ms

                if (tabLayout.getSelectedTabPosition() == 0) {
                    sendMessage();
                } else {
                    sendEmailMessage();
                }
            }
        });
        getUserConversation();

        // Set up Spinner adapter
        templateListAdapter = new EmailTemplateAdapter(this, messageTemplate);
        binding.templateSpinner.setAdapter(templateListAdapter);

        // Handle template selection
        binding.templateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Ignore first item
                    binding.messageInput.setHtml(messageTemplate.get(position).getBody());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getTemplates("1");

        binding.recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboard();
            }
            return false;
        });
        binding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboard();
            }
            return false;
        });

        activityLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            getUserConversation();
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                // Retrieve data from the result
//                                String resultValue = result.getData().getStringExtra("key"); // Replace "key" with your actual key
//                                Toast.makeText(requireContext(), "Result: " + resultValue, Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void setHightImageRecycle(){
        if(selectedImages.isEmpty()){
            ViewGroup.LayoutParams layoutParams = binding.imageRecycleList.getLayoutParams();
            layoutParams.height = 0; // Set height in pixels
            binding.imageRecycleList.setLayoutParams(layoutParams);
        }else {
            ViewGroup.LayoutParams layoutParams = binding.imageRecycleList.getLayoutParams();
            layoutParams.height = 200; // Set height in pixels
            binding.imageRecycleList.setLayoutParams(layoutParams);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getUserConversation() {
        if(!isLoading){
            // Create an ExecutorService to run the login task in the background
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(() -> {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        binding.loader.setVisibility(View.VISIBLE);
                        isLoading = true;
                    }
                });
                ApiService apiService = new ApiService();

                ChatConversationResponse loginResponse = apiService.getUserConversation(getIntent().getStringExtra("userid"));

                if (loginResponse != null && loginResponse.getStatus()) {
                    messages.clear();
                    messages.addAll(loginResponse.getData().getConversations());
                    runOnUiThread(() -> {
                        // Update your UI elements here
                        chatAdapter.notifyDataSetChanged();
                        binding.recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                        binding.loader.setVisibility(View.GONE);
                        isLoading = false;
                    });

                } else {
                    binding.loader.setVisibility(View.GONE);
                    isLoading = false;
                    // Handle login failure
                }
            });
        }

    }

    private void sendMessage() {
        String text = binding.messageInput.getHtml();
        if (text==null||text.isEmpty()) {
            Toast.makeText(this, "Please fill detail", Toast.LENGTH_SHORT).show();
        } else {
            binding.loader.setVisibility(View.VISIBLE);
            // Your API call logic here
            Log.d("API_CALL", "API called with phone number: " + text);

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(() -> {
                ApiService apiService = new ApiService();


                ArrayList<String> filePaths = new ArrayList<>();
                for (Uri uri : selectedImages) {
                    String fileName = getFileFromUri(this, uri); // ✅ Correct
                    filePaths.add(fileName);
                }

                apiService.sendConversation(this, text, "", userPhone, filePaths);
                runOnUiThread(() -> {
                    selectedImages.clear();
                    binding.messageInput.setHtml("");
                    binding.recipientField.setText("");
                    binding.loader.setVisibility(View.GONE);
                    imageAdapter.notifyDataSetChanged();
                    setHightImageRecycle();
                    binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
                    getUserConversation();
                });

            });
        }


    }
//    private void sendMessage() {
//        String text = binding.messageInput.getHtml().toString().trim();
//        if (text.isEmpty()) return;
//
//        binding.loader.setVisibility(View.VISIBLE);
//        // Your API call logic here
//        Log.d("API_CALL", "API called with phone number: " + text);
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(() -> {
//            ApiService apiService = new ApiService();
//            binding.messageInput.setHtml("");
//
//            apiService.sendConversation(this, text, "", userPhone, new ArrayList<>());
//            runOnUiThread(() -> {
//                binding.loader.setVisibility(View.GONE);
//                getUserConversation();
//
//            });
//
//        });
//    }


    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*"); // Allow all file types
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // For multiple selection

        filePickerLauncher.launch(Intent.createChooser(intent, "Select Files"));
    }

    public static String getFileFromUri(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String fileExtension = getFileExtension(context, uri);
        String fileName = "file_" + System.currentTimeMillis() + "." + fileExtension;
        File file = new File(context.getCacheDir(), fileName);

        try (InputStream inputStream = contentResolver.openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            if (inputStream != null) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file.getAbsolutePath();
    }

    // Function to get file extension
    public static String getFileExtension(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String mimeType = contentResolver.getType(uri);
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) != null
                ? MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                : "unknown";
    }
//    private void sendEmailMessage() {
//
//        String message = binding.messageInput.getHtml().toString();
//        String subject = binding.recipientField.getText().toString().trim();
//
//        if (subject.isEmpty() || message.isEmpty()) {
//            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        binding.loader.setVisibility(View.VISIBLE);
//
//        // Simulate API call using background thread
//        new Thread(() -> {
//            try {
//                // Simulate a network operation
//                Thread.sleep(2000);
//
//                // API call simulation
//                ApiService apiService = new ApiService();
//                ArrayList<String> filePaths = new ArrayList<>();
//
//
//                apiService.sendEmail(
//                        this,
//                        userEmail,
//                        subject,
//                        message,
//                        "",
//                        filePaths
//                );
//
//                // Update UI on the main thread
//                runOnUiThread(() -> {
//                    binding.recipientField.setText("");
//                    binding.messageInput.setHtml("");
//                    binding.loader.setVisibility(View.GONE);
//                    getUserConversation();
//                });
//
//            } catch (InterruptedException e) {
//                runOnUiThread(() -> {
//                    binding.loader.setVisibility(View.GONE);
//                    Toast.makeText(this, "Failed to send email!", Toast.LENGTH_SHORT).show();
//                });
//            }
//        }).start();
//    }

    private void sendEmailMessage() {
        String subject = String.valueOf(binding.recipientField.getText());
        String message = binding.messageInput.getHtml();

        if (message == null || subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.loader.setVisibility(View.VISIBLE);

        // Simulate API call using background thread
        new Thread(() -> {
            try {
                // Simulate a network operation
                Thread.sleep(2000);

                // API call simulation
                ApiService apiService = new ApiService();
                ArrayList<String> filePaths = new ArrayList<>();
                for (Uri uri : selectedImages) {
                    String fileName = getFileFromUri(this, uri); // ✅ Correct
                    filePaths.add(fileName);
                }

                apiService.sendEmail(
                        this,
                        UserSharedPreferences.userEmail,
                        userEmail,
                        subject,
                        message,
                        "",
                        filePaths
                );

                // Update UI on the main thread
                runOnUiThread(() -> {
                    binding.loader.setVisibility(View.GONE);
//                    Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
                    binding.recipientField.setText("");
                    binding.messageInput.setHtml("");
                    selectedImages.clear();
                    imageAdapter.notifyDataSetChanged();
                    binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
                    setHightImageRecycle();
                    getUserConversation();
                });

            } catch (InterruptedException e) {
                runOnUiThread(() -> {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to send email!", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    void getTemplates(String type) {
        // Create an ExecutorService to run the login task in the background
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            EmailMessageTemplateResponse loginResponse = apiService.getSmsEmailTemplate(type);
            if (loginResponse != null) {
                runOnUiThread(() -> {
                    // Successfully logged in, navigate to the main activity
                    messageTemplate.clear();
                    if (loginResponse.getData() != null) {
                        messageTemplate.addAll(loginResponse.getData());

                    } else {
                        messageTemplate.clear();
                    }
                    binding.templateSpinner.setAdapter(templateListAdapter);
                    templateListAdapter.notifyDataSetChanged();
                });
            }



        });
    }

   public void readMessage(String messageID,String senderId) {
        // Create an ExecutorService to run the login task in the background
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            Response loginResponse = apiService.markReadMessage(messageID,senderId);
            if (loginResponse != null) {
                runOnUiThread(() -> {
                    binding.templateSpinner.setAdapter(templateListAdapter);
                    templateListAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void handleSelectedFiles(Intent data) {

        if (data.getClipData() != null) { // Multiple files
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                Uri fileUri = data.getClipData().getItemAt(i).getUri();

                //   String fullPath = getRealPathFromUri(getActivity(), fileUri);
                selectedImages.add(fileUri);
                imageAdapter.notifyItemInserted(selectedImages.size() - 1);
                binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
            }
        } else if (data.getData() != null) { // Single file
            Uri fileUri = data.getData();
//            String fullPath = getRealPathFromUri(getActivity(), fileUri);
            selectedImages.add(fileUri);
            imageAdapter.notifyItemInserted(selectedImages.size() - 1);
            binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
        }

        for (Uri path : selectedImages) {
            Log.d("Selected File", path.getPath());
        }
        setHightImageRecycle();
    }

    private void deleteImage(int position) {
        selectedImages.remove(position);
        imageAdapter.notifyItemRemoved(position);
        binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
        setHightImageRecycle();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }
}

