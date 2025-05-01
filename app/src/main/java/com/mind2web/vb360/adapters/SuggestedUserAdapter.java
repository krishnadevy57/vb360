package com.mind2web.vb360.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mind2web.vb360.R;
import com.mind2web.vb360.modeles.Conversations;
import com.mind2web.vb360.modeles.PhoneNumberSuggestedData;
import com.mind2web.vb360.modeles.UserData;
import com.mind2web.vb360.ui.home.HomeFragment;

import java.util.List;


public class SuggestedUserAdapter extends RecyclerView.Adapter<SuggestedUserAdapter.SuggestedUserViewHolder> {
    private List<PhoneNumberSuggestedData> messages;
    private List<String> items; // Replace String with your data type
    private HomeFragment.OnItemClickListener listener;

    public SuggestedUserAdapter(List<PhoneNumberSuggestedData> messages, HomeFragment.OnItemClickListener listener) {
        this.messages = messages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SuggestedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggested_user_item, parent, false);
        return new SuggestedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedUserViewHolder holder, int position) {
        PhoneNumberSuggestedData message = messages.get(position);
        String fullName = message.getUserFullName();
//        String phone = message.getPhone();
//        String lastFourDigits = phone.length() > 4 ? phone.substring(phone.length() - 4) : phone;
        holder.suggestedUserText.setText(fullName);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(message, position);
            }
        });
        holder.suggestedUserPhone.setText(message.getPhone());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class SuggestedUserViewHolder extends RecyclerView.ViewHolder {
        TextView suggestedUserText,suggestedUserPhone;


        public SuggestedUserViewHolder(@NonNull View itemView) {
            super(itemView);
            suggestedUserText = itemView.findViewById(R.id.suggestedUserText);
            suggestedUserPhone = itemView.findViewById(R.id.suggestedUserPhone);
        }
    }
}
