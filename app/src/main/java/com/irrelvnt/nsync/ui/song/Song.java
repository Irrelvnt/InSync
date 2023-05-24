package com.irrelvnt.nsync.ui.song;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.R;
import com.irrelvnt.nsync.clickListener.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class Song implements Serializable {
    String title;
    String url;
    String artist;
    String image;

    public Song(String title, String artist, String image, String url) {
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
        public ImageView imageView;
        public TextView titleView, artistView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView = itemView.findViewById(R.id.titleView);
            artistView = itemView.findViewById(R.id.artistView);

        }

        public void bind(final Song song, final OnItemClickListener listener) {
            titleView.setText(song.getTitle());
            titleView.setMaxLines(2);
            artistView.setMaxLines(1);
            titleView.setEllipsize(TextUtils.TruncateAt.END);
            artistView.setEllipsize(TextUtils.TruncateAt.END);
            artistView.setText(song.getArtist());
            Picasso.get().load(Uri.parse(song.getImage())).transform(new RoundedCornersTransformation(16, 0)).resize(200, 200)
                    .centerCrop().into(imageView);
            itemView.setOnClickListener(v -> listener.onItemClick(song));
        }
    }
}
