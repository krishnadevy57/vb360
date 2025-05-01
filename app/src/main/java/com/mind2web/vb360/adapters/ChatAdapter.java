package com.mind2web.vb360.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mind2web.vb360.ChatActivity;
import com.mind2web.vb360.R;
import com.mind2web.vb360.modeles.ChatData;
import com.mind2web.vb360.modeles.Conversations;
import com.mind2web.vb360.utils.UserSharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Conversations> messages;
    ChatActivity mContext;

    public ChatAdapter(ChatActivity context, List<Conversations> messages) {
        this.messages = messages;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Conversations message = messages.get(position);
        holder.messageText.setInputEnabled(false);
        if (message.getMessage() != null) {
//            Spanned spannedText = HtmlCompat.fromHtml(message.getMessage(), HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.messageText.setHtml(message.getMessage());
            holder.messageText.setInputEnabled(false);
//            holder.messageText.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        }
holder.messageTime.setText(message.getChatTime());
        holder.recieve_chat_user_shortname.setText(message.getUserShortForm());
        holder.send_chat_user_shortname.setText(message.getUserShortForm());
        if (UserSharedPreferences.userId.equals(message.getSenderId())) {
            holder.fm_recieve_chat_icon.setVisibility(View.GONE);
            holder.fm_send_chat_icon.setVisibility(View.VISIBLE);


            if (message.getType().equalsIgnoreCase("1")) {
                holder.sendChatIcon.setImageResource(R.drawable.email);
                holder.messageFromTo.setText(Html.fromHtml(
                        "<b><font color='black'>From:</font></b> " + message.getFromEmailAddress() + "<br>" +
                                "\n<b><font color='black'>To:</font></b> " + message.getToEmailAddress() + "<br>" +
                                "\n<b><font color='black'>Subject:</font></b> " + message.getSubject() + "<br>"
                ));
            } else if (message.getType().equalsIgnoreCase("2")) {
                holder.sendChatIcon.setImageResource(R.drawable.message);
                holder.messageFromTo.setText(Html.fromHtml(
                        "<b><font color='black'>From:</font></b> " + message.getFromPhoneNumber() + "<br>" +
                                "\n<b><font color='black'>To:</font></b> " + message.getToPhoneNumber() + "<br>"
                ));
            } else {

                if (message.getCallStatusLabel().equalsIgnoreCase("missed-call")) {
                    holder.sendChatIcon.setImageResource(R.drawable.ic_call_missed);
                    holder.messageFromTo.setText(Html.fromHtml(
                            "<b><font color='black'>From:</font></b> " + message.getFromDetail() + "<br>" +
                                    "\n<b><font color='black'>To:</font></b> " + message.getToDetail() + "<br><p><b><font color='red'>Missed Call</font></b></p>"
                    ));
                } else {
                    holder.sendChatIcon.setImageResource(R.drawable.phone);
                    holder.messageFromTo.setText(Html.fromHtml(
                            "<b><font color='black'>From:</font></b> " + message.getFromDetail() + "<br>" +
                                    "\n<b><font color='black'>To:</font></b> " + message.getToDetail() + "<br>"
                    ));
                }

            }
        } else {
            holder.fm_recieve_chat_icon.setVisibility(View.VISIBLE);
            if (message.getType().equalsIgnoreCase("1")) {
                holder.recieveChatIcon.setImageResource(R.drawable.email);
                holder.messageFromTo.setText(Html.fromHtml(
                        "<b><font color='black'>From:</font></b> " + message.getFromEmailAddress() + "<br>" +
                                "\n<b><font color='black'>To:</font></b> " + message.getToEmailAddress() + "<br>" +
                                "\n<b><font color='black'>Subject:</font></b> " + message.getSubject() + "<br>"
                ));
            } else if (message.getType().equalsIgnoreCase("2")) {
                holder.recieveChatIcon.setImageResource(R.drawable.message);
                holder.messageFromTo.setText(Html.fromHtml(
                        "<b><font color='black'>From:</font></b> " + message.getFromPhoneNumber() + "<br>" +
                                "\n<b><font color='black'>To:</font></b> " + message.getToPhoneNumber() + "<br>"
                ));
            } else {

                if (message.getCallStatusLabel().equalsIgnoreCase("missed-call")) {
                    holder.recieveChatIcon.setImageResource(R.drawable.ic_call_missed);
                    holder.messageFromTo.setText(Html.fromHtml(
                            "<b><font color='black'>From:</font></b> " + message.getFromDetail() + "<br>" +
                                    "\n<b><font color='black'>To:</font></b> " + message.getToDetail() + "<br><p><b><font color='red'>Missed Call</font></b></p>"
                    ));
                } else {
                    holder.recieveChatIcon.setImageResource(R.drawable.phone);
                    holder.messageFromTo.setText(Html.fromHtml(
                            "<b><font color='black'>From:</font></b> " + message.getFromDetail() + "<br>" +
                                    "\n<b><font color='black'>To:</font></b> " + message.getToDetail() + "<br>"
                    ));
                }
            }

            holder.fm_send_chat_icon.setVisibility(View.GONE);

            if(message.getUnread_chat_users_read_status()!=null && message.getUnread_chat_users_read_status().equalsIgnoreCase("0")){
                mContext.readMessage(message.getMessageId(),message.getSenderId());
            }else {

            }
        }
        if (message.getEmailAttachment()!=null&&message.getEmailAttachment().size()>0) {
            holder.llAttachment.setVisibility(View.VISIBLE);
            // Set Horizontal Layout Manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            holder.attachmentRecycler.setLayoutManager(layoutManager);
            holder.attachmentsAdapter = new AttachmentsAdapter(mContext, message.getEmailAttachment(), this::deleteImage);
            holder.attachmentRecycler.setAdapter(holder.attachmentsAdapter);

        } else if (message.getSmsAttachment()!=null&&message.getSmsAttachment().size()>0) {
            holder.llAttachment.setVisibility(View.VISIBLE);
            // Set Horizontal Layout Manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            holder.attachmentRecycler.setLayoutManager(layoutManager);
            holder.attachmentsAdapter = new AttachmentsAdapter(mContext, message.getSmsAttachment(), this::deleteImage);
            holder.attachmentRecycler.setAdapter(holder.attachmentsAdapter);

        }else {
            holder.llAttachment.setVisibility(View.GONE);
        }

        // Handle Audio Message
        if (message.getCall_recording()!=null&&(!message.getCall_recording().isEmpty())) {
            holder.audioContainer.setVisibility(View.VISIBLE);
            holder.playAudio.setVisibility(View.VISIBLE);
//            holder.pauseAudio.setVisibility(View.VISIBLE);
            holder.audioSeekBar.setVisibility(View.VISIBLE);

            setupAudioPlayer(holder, message.getCall_recording());
        } else {
            holder.audioContainer.setVisibility(View.GONE);
            holder.playAudio.setVisibility(View.GONE);
//            holder.pauseAudio.setVisibility(View.GONE);
            holder.audioSeekBar.setVisibility(View.GONE);
        }

//        holder.itemView.setBackgroundResource(message.isSentByMe()
//                ? R.drawable.sent_message_bg : R.drawable.received_message_bg);

    }

    private void deleteImage(int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView audio_timer,messageTime,messageFromTo, send_chat_user_shortname, recieve_chat_user_shortname;
        RichEditor messageText;
        ImageView sendChatIcon, recieveChatIcon;
        FrameLayout fm_send_chat_icon, fm_recieve_chat_icon;
        AttachmentsAdapter attachmentsAdapter;
        RecyclerView attachmentRecycler;
        LinearLayout llAttachment,audioContainer;
        public ImageButton playAudio, pauseAudio;
        public SeekBar audioSeekBar;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            llAttachment = itemView.findViewById(R.id.llAttachment);
            messageTime = itemView.findViewById(R.id.messageTime);
            audio_timer = itemView.findViewById(R.id.audio_timer);
            messageFromTo = itemView.findViewById(R.id.messageFromTo);
            sendChatIcon = itemView.findViewById(R.id.send_chat_icon);
            recieveChatIcon = itemView.findViewById(R.id.recieve_chat_icon);
            fm_send_chat_icon = itemView.findViewById(R.id.fm_send_chat_icon);
            fm_recieve_chat_icon = itemView.findViewById(R.id.fm_recieve_chat_icon);
            send_chat_user_shortname = itemView.findViewById(R.id.send_chat_user_shortname);
            recieve_chat_user_shortname = itemView.findViewById(R.id.recieve_chat_user_shortname);
            attachmentRecycler = itemView.findViewById(R.id.attachmentRecycler);
// Audio controls
            audioContainer = itemView.findViewById(R.id.audio_container);
            playAudio = itemView.findViewById(R.id.play_audio);
            pauseAudio = itemView.findViewById(R.id.pause_audio);
            audioSeekBar = itemView.findViewById(R.id.audio_seekbar);
        }
    }

    private void setupAudioPlayer(ChatViewHolder holder, ArrayList<String> audioUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        Handler handler = new Handler();

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(audioUrl.get(0));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("AudioPlayer", "Error setting data source", e);
        }

        mediaPlayer.setOnPreparedListener(mp -> {
            Log.d("AudioPlayer", "Audio prepared and ready to play");
            holder.audioSeekBar.setMax(mp.getDuration());

            int currentPos = mediaPlayer.getDuration();
            holder.audio_timer.setText(formatTime(currentPos));
            updateButtonVisibility(holder, false); // Initially show play button
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e("AudioPlayer", "Error: " + what + ", " + extra);
            return false;
        });

        // SeekBar update logic
        Runnable updateSeekbar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    holder.audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    int totalPos = mediaPlayer.getDuration();
                    int currentPos = mediaPlayer.getCurrentPosition();
                    holder.audio_timer.setText(formatTime(totalPos)+"/"+formatTime(currentPos));
                    handler.postDelayed(this, 500); // Update every 500ms
                }
            }
        };

        mediaPlayer.setOnCompletionListener(mp -> {
            holder.audioSeekBar.setProgress(0);
            handler.removeCallbacks(updateSeekbar);
            updateButtonVisibility(holder, false); // Show play button when completed
            Log.d("AudioPlayer", "Audio playback completed");
        });

        holder.playAudio.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                Log.d("AudioPlayer", "Playing audio...");
                mediaPlayer.start();
                handler.post(updateSeekbar); // Start updating SeekBar when audio plays
                updateButtonVisibility(holder, true); // Show pause button when playing
            }
        });

        holder.pauseAudio.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                Log.d("AudioPlayer", "Pausing audio...");
                mediaPlayer.pause();
                handler.removeCallbacks(updateSeekbar); // Stop updating SeekBar when paused
                updateButtonVisibility(holder, false); // Show play button when paused
            }
        });

        holder.audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // Helper function to toggle visibility of play and pause buttons
    private void updateButtonVisibility(ChatViewHolder holder, boolean isPlaying) {
        holder.audioSeekBar.setVisibility(View.VISIBLE);
        if (isPlaying) {
            holder.playAudio.setVisibility(View.GONE);
            holder.pauseAudio.setVisibility(View.VISIBLE);
        } else {
            holder.playAudio.setVisibility(View.VISIBLE);
            holder.pauseAudio.setVisibility(View.GONE);
        }
    }
    // Format time method
    private String formatTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

