package com.mind2web.vb360.ui.dashboard;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mind2web.vb360.ChatActivity;
import com.mind2web.vb360.R;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.modeles.UserData;
import com.mind2web.vb360.modeles.UserListResponse;
import com.mind2web.vb360.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatUserAdapter adapter;
    private List<UserData> userChatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_user_list); // Use a corresponding layout file

        recyclerView = findViewById(R.id.chat_user_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatUserAdapter(userChatList);
        recyclerView.setAdapter(adapter);

        getUserList();
    }

    // Inner class for adapter
    class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

        private final List<UserData> userChatList;

        public ChatUserAdapter(List<UserData> userChatList) {
            this.userChatList = userChatList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_chat_user, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            UserData user = userChatList.get(position);
            holder.nameTextView.setText(user.getCustomUserName());
            holder.lastMessageTimeTextView.setText(user.getFinalLastConversationTime());
            holder.chatTypeTextView.setText(user.getLastChatType());
            holder.chatUserShortName.setText(user.getUserShortForm());

            holder.chatUserItem.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, ChatActivity.class);
                intent.putExtra("userid", userChatList.get(position).getUserId());
                intent.putExtra("userphone", userChatList.get(position).getPhone());
                intent.putExtra("username", userChatList.get(position).getCustomUserName());
                intent.putExtra("shortname", userChatList.get(position).getUserShortForm());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return userChatList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView, lastMessageTimeTextView, chatTypeTextView;
            TextView chatUserShortName;
            LinearLayout chatUserItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.chat_user_name);
                chatUserItem = itemView.findViewById(R.id.chat_user_item);
                lastMessageTimeTextView = itemView.findViewById(R.id.chat_last_message_time);
                chatTypeTextView = itemView.findViewById(R.id.chat_last_message_type);
                chatUserShortName = itemView.findViewById(R.id.chat_user_shortname);
            }
        }
    }

    private void getUserList() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            UserListResponse loginResponse = apiService.getUserList(1, 20,"");
            if (loginResponse != null) {
                runOnUiThread(() -> {
                    if (loginResponse != null && loginResponse.getStatus()) {
                        userChatList.clear();
                        userChatList.addAll(loginResponse.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DashboardActivity.this, "Contacts failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
