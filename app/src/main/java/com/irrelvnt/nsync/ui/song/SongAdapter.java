package com.irrelvnt.nsync.ui.song;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    Context context;
    List<Song> songs;

    public SongAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(context).inflate(R.layout.song_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.titleView.setText(songs.get(position).getTitle());
        holder.artistView.setText(songs.get(position).getArtist());
        holder.imageView.setImageResource(songs.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
