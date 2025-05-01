package com.mind2web.vb360.ui.settings;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mind2web.vb360.adapters.EmailTemplateAdapter;
import com.mind2web.vb360.adapters.ImageListAdapter;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.databinding.FragmentSendEmailsBinding;
import com.mind2web.vb360.modeles.EmailMessageTemplateData;
import com.mind2web.vb360.modeles.EmailMessageTemplateResponse;
import com.mind2web.vb360.utils.UserSharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendEmailFragment extends Fragment {


    private FragmentSendEmailsBinding binding;
    private List<Uri> selectedImages = new ArrayList<>();

    private String isSchedule = "";

    private ActivityResultLauncher<Intent> filePickerLauncher;

    ImageListAdapter imageAdapter;
    private List<EmailMessageTemplateData> messageTemplate = new ArrayList<EmailMessageTemplateData>();

    EmailTemplateAdapter templateListAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SendEmailViewModel sendEmailViewModel =
                new ViewModelProvider(this).get(SendEmailViewModel.class);

        binding = FragmentSendEmailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Handle send button click
        binding.sendButton.setOnClickListener(view -> sendEmailMessage());

        // Handle image selection button click
        binding.selectImagesButton.setOnClickListener(view -> openFileChooser());

       // Set Horizontal Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

         imageAdapter = new ImageListAdapter(getActivity(),selectedImages, this::deleteImage);
        binding.recyclerView.setAdapter(imageAdapter);

        binding.selectImagesButton.setText("Attachments("+selectedImages.size()+")");

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
        templateListAdapter = new EmailTemplateAdapter(getActivity(), messageTemplate);
        binding.templateSpinner.setAdapter(templateListAdapter);

        // Handle template selection
        binding.templateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Ignore first item
                    binding.messageEditor.setHtml(messageTemplate.get(position).getBody());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        getTemplates();
        return root;
    }

    private void handleSelectedFiles(Intent data) {

        if (data.getClipData() != null) { // Multiple files
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                Uri fileUri = data.getClipData().getItemAt(i).getUri();

                //   String fullPath = getRealPathFromUri(getActivity(), fileUri);
                selectedImages.add(fileUri);
                imageAdapter.notifyItemInserted(selectedImages.size() - 1);
                binding.selectImagesButton.setText("Attachments("+selectedImages.size()+")");
            }
        } else if (data.getData() != null) { // Single file
            Uri fileUri = data.getData();
//            String fullPath = getRealPathFromUri(getActivity(), fileUri);
            selectedImages.add(fileUri);
            imageAdapter.notifyItemInserted(selectedImages.size() - 1);
            binding.selectImagesButton.setText("Attachments("+selectedImages.size()+")");
        }

        for (Uri path : selectedImages) {
            Log.d("Selected File", path.getPath());
        }
    }

    private void deleteImage(int position) {
        selectedImages.remove(position);
        imageAdapter.notifyItemRemoved(position);
        binding.selectImagesButton.setText("Attachments("+selectedImages.size()+")");
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



    private void sendEmailMessage() {
        String recipient = binding.recipientField.getText().toString().trim();
        String subject = binding.subjectField.getText().toString().trim();
        String message = binding.messageEditor.getHtml();

        if (recipient.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all details", Toast.LENGTH_SHORT).show();
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
                    String fileName = getFileFromUri(getContext(), uri); // ✅ Correct
                    filePaths.add(fileName);
                }

                apiService.sendEmail(
                        getContext(),
                        UserSharedPreferences.userEmail,
                        recipient,
                        subject,
                        message,
                        isSchedule,
                        filePaths
                );

                // Update UI on the main thread
                requireActivity().runOnUiThread(() -> {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Email sent successfully!", Toast.LENGTH_SHORT).show();
                    binding.subjectField.setText("");
                    binding.recipientField.setText("");
                    binding.messageEditor.setHtml("");
                    selectedImages.clear();
                    imageAdapter.notifyDataSetChanged();
                    binding.selectImagesButton.setText("Attachments("+selectedImages.size()+")");
                });

            } catch (InterruptedException e) {
                requireActivity().runOnUiThread(() -> {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to send email!", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    void getTemplates(){
        // Create an ExecutorService to run the login task in the background
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            EmailMessageTemplateResponse loginResponse = apiService.getSmsEmailTemplate("0");
            if(loginResponse!=null) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        // Successfully logged in, navigate to the main activity
                        messageTemplate.clear();
                        if(loginResponse.getData()!=null){
                            messageTemplate.addAll(loginResponse.getData());

                        }else {
                            messageTemplate.clear();
                        }
                        binding.templateSpinner.setAdapter(templateListAdapter);
                        templateListAdapter.notifyDataSetChanged();
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
