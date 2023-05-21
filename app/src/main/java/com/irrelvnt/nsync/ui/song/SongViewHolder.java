package com.irrelvnt.nsync.ui.song;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.R;

public class SongViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView titleView, artistView;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        titleView = itemView.findViewById(R.id.titleView);
        artistView = itemView.findViewById(R.id.artistView);

    }
}
