//package com.mind2web.vb360.ui.chats;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.tabs.TabLayout;
//import com.mind2web.vb360.EmailActivity;
//import com.mind2web.vb360.R;
//import com.mind2web.vb360.SendMessageActivity;
//import com.mind2web.vb360.adapters.ChatAdapter;
//import com.mind2web.vb360.apiservice.ApiService;
//import com.mind2web.vb360.modeles.ChatConversationResponse;
//import com.mind2web.vb360.modeles.Conversations;
//import com.mind2web.vb360.utils.Utils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class ChatFragment extends Fragment {
//    ArrayList<String> filePaths=new ArrayList<>();
//
//    private RecyclerView recyclerView;
//    private EditText messageInput;
//    private ImageButton sendButton;
//    private ChatAdapter chatAdapter;
//    private List<Conversations> messages = new ArrayList<>();
//    private ProgressBar loader;
//    private String userPhone;
//
//    public ChatFragment() {
//        // Required empty public constructor
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_chat, container, false);
//
//        // Retrieve arguments
//        Bundle args = getArguments();
//        userPhone = args != null ? args.getString("userphone") : "";
//
//        TabLayout tabLayout = view.findViewById(R.id.tabLayout1);
//        ImageView expendBtn = view.findViewById(R.id.expendBtn);
//        recyclerView = view.findViewById(R.id.recyclerView);
//        messageInput = view.findViewById(R.id.messageInput);
//        sendButton = view.findViewById(R.id.sendButton);
//        loader = view.findViewById(R.id.loader);
//        loader.setVisibility(View.GONE);
//
//        expendBtn.setOnClickListener(v -> {
//            if (tabLayout.getSelectedTabPosition() == 0) {
//                Intent intent = new Intent(getContext(), SendMessageActivity.class);
//                intent.putExtra("message", messageInput.getText().toString());
//                intent.putExtra("userphone", userPhone);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(getContext(), EmailActivity.class);
//                intent.putExtra("message", messageInput.getText().toString());
//                intent.putExtra("userphone", userPhone);
//                startActivity(intent);
//            }
//        });
//
//        messageInput.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEND) {
//                String message = messageInput.getText().toString();
//                if (!message.isEmpty()) {
//                    sendMessage();
//                }
//                return true;
//            }
//            return false;
//        });
//
//        chatAdapter = new ChatAdapter(messages);
//        recyclerView.setAdapter(chatAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        sendButton.setOnClickListener(v -> sendMessage());
//
//        getUserConversation();
//
//        return view;
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private void getUserConversation() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(() -> {
//            requireActivity().runOnUiThread(() -> loader.setVisibility(View.VISIBLE));
//
//            ApiService apiService = new ApiService();
//            ChatConversationResponse response = apiService.getUserConversation(getArguments().getString("userid"));
//            if (response != null && response.getStatus()) {
//
//                messages.clear();
//                messages.addAll(response.getData().getConversations());
//
//                requireActivity().runOnUiThread(() -> {
//                    chatAdapter.notifyDataSetChanged();
//                    recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
//                    loader.setVisibility(View.GONE);
//                });
//            } else {
//                requireActivity().runOnUiThread(() -> loader.setVisibility(View.GONE));
//            }
//        });
//    }
//
//    private void sendMessage() {
//        String text = messageInput.getText().toString().trim();
//        if (text.isEmpty()) return;
//
//        loader.setVisibility(View.VISIBLE);
//        Log.d("API_CALL", "API called with phone number: " + text);
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(() -> {
//            ApiService apiService = new ApiService();
//            messageInput.setText("");
//            apiService.sendConversation(requireContext(), text,"", userPhone,filePaths);
//
//            requireActivity().runOnUiThread(() -> {
//                loader.setVisibility(View.GONE);
//                getUserConversation();
//            });
//        });
//    }
//}
