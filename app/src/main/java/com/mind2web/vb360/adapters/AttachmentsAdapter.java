package com.mind2web.vb360.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mind2web.vb360.R;

import java.io.File;
import java.util.List;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.ImageViewHolder> {
    private List<String> imageList;
    Context mContext;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public AttachmentsAdapter(Context context,List<String> imageList, OnDeleteClickListener listener) {
        this.imageList = imageList;
        this.mContext=context;
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        String imageUrl = imageList.get(position); // Replace with your image URL

        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.ic_dashboard_black_24dp) // Optional: add a placeholder image
                .error(R.drawable.ic_dashboard_black_24dp) // Optional: add an error image
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private String getFileNameWithExtension(Context context, Uri uri) {
        String fileName = null;

        // Handle content scheme URIs
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        fileName = cursor.getString(nameIndex);  // Get file name with extension
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        // Handle file scheme URIs
        if (fileName == null) {
            fileName = new File(uri.getPath()).getName();
        }

        return fileName;
    }

}
