package com.irrelvnt.nsync.ui.songList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.R;
import com.irrelvnt.nsync.clickListener.OnItemClickListener;
import com.irrelvnt.nsync.ui.song.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<Song.ViewHolder> {

    final List<Song> songs;
    private final OnItemClickListener listener;

    public SongAdapter(List<Song> songs, OnItemClickListener listener) {
        this.songs = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Song.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Song.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Song.ViewHolder holder, int position) {
        holder.bind(songs.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
