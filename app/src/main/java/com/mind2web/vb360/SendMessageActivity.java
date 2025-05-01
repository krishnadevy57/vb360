package com.mind2web.vb360;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.mind2web.vb360.adapters.EmailTemplateAdapter;
import com.mind2web.vb360.adapters.ImageListAdapter;
import com.mind2web.vb360.adapters.SuggestedUserAdapter;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.databinding.ActivitySendMessageBinding;
import com.mind2web.vb360.databinding.FragmentSendMessagesBinding;
import com.mind2web.vb360.modeles.EmailMessageTemplateData;
import com.mind2web.vb360.modeles.EmailMessageTemplateResponse;
import com.mind2web.vb360.modeles.PhoneNumberSuggestedData;
import com.mind2web.vb360.modeles.PhoneNumberSuggestionsResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMessageActivity extends AppCompatActivity {

    String userPhone, message;
    ActivitySendMessageBinding binding;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable apiCallRunnable;
    private SuggestedUserAdapter adapter;
    private List<PhoneNumberSuggestedData> suggestedUserList = new ArrayList<>();
    boolean isSelctedContact = false;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    private List<Uri> selectedImages = new ArrayList<>();
    private ActivityResultLauncher<Intent> filePickerLauncher;
    ImageListAdapter imageAdapter;
    private List<EmailMessageTemplateData> messageTemplate = new ArrayList<EmailMessageTemplateData>();
    EmailTemplateAdapter templateListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Send Message");

        }

        // Handle back button click

        userPhone = getIntent().getStringExtra("userphone");
        message = getIntent().getStringExtra("message");

        ArrayList<Uri> selectedImage = getIntent().getParcelableArrayListExtra("selected_images");

        if (selectedImage != null) {
            for (Uri uri : selectedImage) {
                Log.d("Image URI", uri.toString());
                selectedImages.add(uri);
            }
        }

        binding.messageField.setHtml(message);
        binding.recipientField.setText(userPhone);

        binding.suggestedUserListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuggestedUserAdapter(suggestedUserList, (item, position) -> {
            // Handle the click event here
            isSelctedContact = true;
            detectCountryFromPhoneNumber(item.getPhone());

            suggestedUserList.clear();

            adapter.notifyDataSetChanged();
        });


        binding.selectDateTimeButton.setOnClickListener(v -> showDateTimePicker());

        binding.selectCameraImageButton.setOnClickListener(v -> openCamera());
        binding.selectGalleryImageButton.setOnClickListener(v -> openGallery());

        binding.scheduleCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.dateTimeLayout.setVisibility(View.VISIBLE);
            } else {
                binding.dateTimeLayout.setVisibility(View.GONE);
            }
        });

        binding.recipientField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Close the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(binding.recipientField.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });

        binding.recipientField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (apiCallRunnable != null) {
                    handler.removeCallbacks(apiCallRunnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isSelctedContact) {
                    isSelctedContact = false;
                } else {
                    apiCallRunnable = () -> callApi(binding.recipientField.getText().toString());
                    handler.postDelayed(apiCallRunnable, 300);
                }

            }
        });

        binding.suggestedUserListRecyclerView.setAdapter(adapter);
        binding.loader.setVisibility(View.GONE);
        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Handle image selection button click
        binding.selectImagesButton.setOnClickListener(view -> openFileChooser());

// Set Horizontal Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        imageAdapter = new ImageListAdapter(this, selectedImages, this::deleteImage);
        binding.recyclerView.setAdapter(imageAdapter);

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


        // Set up Spinner adapter
        templateListAdapter = new EmailTemplateAdapter(this, messageTemplate);
        binding.templateSpinner.setAdapter(templateListAdapter);

        // Handle template selection
        binding.templateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Ignore first item
                    binding.messageField.setHtml(messageTemplate.get(position).getBody());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getTemplates();
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

    private void handleSelectedFiles(Intent data) {

        if (data.getClipData() != null) { // Multiple files
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                Uri fileUri = data.getClipData().getItemAt(i).getUri();
                selectedImages.add(fileUri);
                imageAdapter.notifyItemInserted(selectedImages.size() - 1);
                binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
            }
        } else if (data.getData() != null) { // Single file
            Uri fileUri = data.getData();
            selectedImages.add(fileUri);
            imageAdapter.notifyItemInserted(selectedImages.size() - 1);
            binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
        }

        for (Uri path : selectedImages) {
            Log.d("Selected File", path.getPath());
        }
    }

    private void deleteImage(int position) {
        selectedImages.remove(position);
        imageAdapter.notifyItemRemoved(position);
        binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
    }

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


    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private void detectCountryFromPhoneNumber(String phone) {
//if(phone.contains("+")){
//
//}else {
//    phone="+"+phone;
//}
        // Initialize PhoneNumberUtil
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            // Parse the phone number
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(phone, null);

            // Extract the country code
            int countryCode = parsedNumber.getCountryCode();
            binding.countryCodePicker.setCountryForPhoneCode(countryCode);
            System.out.println("Country Code: +" + countryCode);

            // Extract the national number
            long nationalNumber = parsedNumber.getNationalNumber();
            System.out.println("Phone Number: " + nationalNumber);
            binding.recipientField.setText("" + nationalNumber);

//            updatePhoneNumberDisplay();

        } catch (NumberParseException e) {
            binding.recipientField.setText("" + phone);
            System.err.println("NumberParseException: " + e.toString());
//            updatePhoneNumberDisplay();
        }
    }

    boolean isSendingData = false;
    private void sendMessage() {
        if(!isSendingData) {
            String text = binding.messageField.getHtml();
            String recipient = binding.recipientField.getText().toString().trim();
            if (text.isEmpty() || recipient.isEmpty()) {
                Toast.makeText(this, "Please fill detail", Toast.LENGTH_SHORT).show();
            } else {
                binding.loader.setVisibility(View.VISIBLE);
                isSendingData = true;
                // Your API call logic here
                Log.d("API_CALL", "API called with phone number: " + text);

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.execute(() -> {
                    ApiService apiService = new ApiService();

                    String selectTime = "";
                    if (binding.scheduleCheckbox.isChecked()) {
                        selectTime = binding.selectedDateTimeText.getText().toString();
                    } else {
                        selectTime = "";
                    }
                    ArrayList<String> filePaths = new ArrayList<>();
                    for (Uri uri : selectedImages) {
                        String fileName = getFileFromUri(this, uri); // ✅ Correct
                        filePaths.add(fileName);
                    }

                    apiService.sendConversation(this, text, selectTime, binding.recipientField.getText().toString(), filePaths);
                    runOnUiThread(() -> {
                        selectedImages.clear();
                        binding.messageField.setHtml("");
                        binding.recipientField.setText("");
                        binding.loader.setVisibility(View.GONE);
                        isSendingData = false;
                        imageAdapter.notifyDataSetChanged();
                        binding.selectImagesButton.setText("Attachments(" + selectedImages.size() + ")");
                    });

                });
            }
        }
    }


    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        // DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    // TimePickerDialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (view1, hourOfDay, minute) -> {
                                String time = hourOfDay + ":" + minute;
                                binding.selectedDateTimeText.setText(date + " " + time);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true);
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }


    private void callApi(String phoneNumber) {
        // Your API call logic here
        Log.d("API_CALL", "API called with phone number: " + phoneNumber);
        suggestedUserList.clear();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            PhoneNumberSuggestionsResponse phoneNumberSuggestionsResponse = apiService.getPhoneNumbersSuggestions(phoneNumber);
            runOnUiThread(() -> {
                if (phoneNumberSuggestionsResponse != null) {
                    suggestedUserList.clear();
                    if (phoneNumberSuggestionsResponse.getData() != null) {
                        suggestedUserList.addAll(phoneNumberSuggestionsResponse.getData());
                    } else {
                        suggestedUserList.clear();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    suggestedUserList.clear();
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }


    void getTemplates() {
        // Create an ExecutorService to run the login task in the background
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            EmailMessageTemplateResponse loginResponse = apiService.getSmsEmailTemplate("1");
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
}
