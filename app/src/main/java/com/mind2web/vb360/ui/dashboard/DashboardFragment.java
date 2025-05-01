package com.mind2web.vb360.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mind2web.vb360.ChatActivity;
import com.mind2web.vb360.MyApplication;
import com.mind2web.vb360.R;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.modeles.EmailMessageTemplateResponse;
import com.mind2web.vb360.modeles.PhoneNumberSuggestionsResponse;
import com.mind2web.vb360.modeles.UserData;
import com.mind2web.vb360.modeles.UserListResponse;
import com.mind2web.vb360.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatUserAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<UserData> userChatList = new ArrayList<>();
    SearchView svSearchUser;
    private ActivityResultLauncher<Intent> activityLauncher;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_user_list, container, false);

        recyclerView = rootView.findViewById(R.id.chat_user_list_recycler_view);
        svSearchUser = rootView.findViewById(R.id.sv_search_user);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ChatUserAdapter(userChatList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            getUserList("");
            swipeRefreshLayout.setRefreshing(false);
        });
        svSearchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Handler handler = new Handler();
            private Runnable searchRunnable;

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search on submit
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Cancel the previous task if the user is still typing
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> performSearch(newText);
                handler.postDelayed(searchRunnable, 300); // 300 ms delay

                return true;
            }

            private void performSearch(String query) {
                // Your search API logic here
                getUserList(query);
            }
        });
        getUserList("");
        activityLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            getUserList(svSearchUser.getQuery().toString());
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                // Retrieve data from the result
//                                String resultValue = result.getData().getStringExtra("key"); // Replace "key" with your actual key
//                                Toast.makeText(requireContext(), "Result: " + resultValue, Toast.LENGTH_SHORT).show();
                            }
                        });
        return rootView;
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user, parent, false);


            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            UserData user = userChatList.get(position);
            holder.nameTextView.setText(user.getCustomUserName());
            holder.lastMessageTimeTextView.setText(user.getFinalLastConversationTime());
            if (user.getUnreadCount() != null && user.getUnreadCount().equalsIgnoreCase("0")) {
                holder.chatTypeTextView.setText("");
                holder.chatTypeTextView.setVisibility(View.GONE);
            } else {
                if (user.getUnreadCount() != null)
                    holder.chatTypeTextView.setText(user.getUnreadCount());
                holder.chatTypeTextView.setVisibility(View.VISIBLE);
            }
            if (user.getUserShortForm() != null)
                holder.chatUserShortName.setText(user.getUserShortForm());

            if (user.getLastChatType() != null && user.getLastChatType().equalsIgnoreCase("1")) {
                holder.sendChatIcon.setImageResource(R.drawable.email);
            } else if (user.getLastChatType() != null && user.getLastChatType().equalsIgnoreCase("2")) {
                holder.sendChatIcon.setImageResource(R.drawable.message);
            } else {
                holder.sendChatIcon.setImageResource(R.drawable.phone);
            }


            holder.chatUserItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("userid", userChatList.get(position).getUserId());
                    intent.putExtra("userphone", userChatList.get(position).getPhone());
                    intent.putExtra("useremail", userChatList.get(position).getEmail());

                    intent.putExtra("username", userChatList.get(position).getCustomUserName());
                    intent.putExtra("shortname", userChatList.get(position).getUserShortForm());
                    String firstName = userChatList.get(position).getFirstName();
                    String lasstName = userChatList.get(position).getLastName();
                    if(firstName!=null){
                        intent.putExtra("fullname", firstName+" "+lasstName);

                    }else {
                        intent.putExtra("fullname", "");
                    }

                    activityLauncher.launch(intent);

//
//                    Bundle args = new Bundle();
//                    args.putString("userphone", userChatList.get(position).getPhone()); // Replace with actual data
//                    args.putString("userid", userChatList.get(position).getUserId()); // Replace with actual data
//
//                    ChatFragment chatFragment = new ChatFragment();
//                    chatFragment.setArguments(args);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//// Replace the fragment container with the ChatFragment
//
//                    FrameLayout fragmentContainer = (FrameLayout) getActivity().findViewById(R.id.fragment_container);
//
//
//
//// Optionally, add the transaction to the back stack (for navigation purposes)
//                    transaction.addToBackStack(null);
//
//// Commit the transaction
//                    transaction.commit();
//
//                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//                    ChatFragment newFragment = new ChatFragment();  // Create the fragment you want to call
//                    fragmentTransaction.replace(R.id.fragment_container, newFragment);  // Replace the current fragment
//                    fragmentTransaction.addToBackStack(null);  // Optional: to allow navigating back
//                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return userChatList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView, lastMessageTimeTextView, chatTypeTextView;
            TextView chatUserShortName;
            ImageView sendChatIcon;
            LinearLayout chatUserItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.chat_user_name);
                chatUserItem = itemView.findViewById(R.id.chat_user_item);
                lastMessageTimeTextView = itemView.findViewById(R.id.chat_last_message_time);
                chatTypeTextView = itemView.findViewById(R.id.chat_last_message_type);
                chatUserShortName = itemView.findViewById(R.id.chat_user_shortname);
                sendChatIcon = itemView.findViewById(R.id.send_chat_icon);

            }
        }
    }


    private void getUserList(String search) {
        // Create an ExecutorService to run the login task in the background
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            UserListResponse loginResponse = apiService.getUserList(1, 20, search);
            if (loginResponse != null) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (loginResponse.getStatus() != null && loginResponse.getStatus()) {
                            // Successfully logged in, navigate to the main activity
                            if (loginResponse.getData() != null) {
                                userChatList.clear();
                                userChatList.addAll(loginResponse.getData());
                                adapter.notifyDataSetChanged();
                            } else {
                                userChatList.clear();
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            // Handle login failure
                            Toast.makeText(getActivity(), "Contacts failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
