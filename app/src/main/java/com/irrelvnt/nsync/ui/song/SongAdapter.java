package com.irrelvnt.nsync.ui.song;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    List<Song> songs;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.titleView.setText(songs.get(position).getTitle());
        holder.artistView.setText(songs.get(position).getArtist());
        Picasso.get().load(Uri.parse(songs.get(position).getImage())).resize(200, 200)
                .centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
