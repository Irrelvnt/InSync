package com.irrelvnt.nsync;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.ui.home.HomeFragment;
import com.irrelvnt.nsync.ui.song.Song;
import com.irrelvnt.nsync.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Player implements Serializable {
    private static MediaPlayer player;
    public static List<Song> selectedSongs = new ArrayList<>();
    private static Song playing;
    public static List<Song> nowPlaying = new ArrayList<>();
    public static List<Song> fetchedVideos = new ArrayList<>();
    public static Context context;

    public static void setPlayPauseButtons(ImageButton play, ImageButton pause) {
        play.setOnClickListener(v -> {
            if (player == null) {
                initializePlayer();
            } else {
                player.start();
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
            }
        });
        pause.setOnClickListener(v -> {
            if (player == null) {
                initializePlayer();
            } else {
                player.pause();
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
            }
        });
    }


    public static void selectSong(Song song, RecyclerView recyclerView, int index) {
        if (!selectedSongs.contains(song)) {
            selectedSongs.add(song);
            recyclerView.getChildAt(index).findViewById(R.id.add).setVisibility(View.GONE);
            recyclerView.getChildAt(index).findViewById(R.id.remove).setVisibility(View.VISIBLE);
        } else {
            selectedSongs.remove(song);
            recyclerView.getChildAt(index).findViewById(R.id.add).setVisibility(View.VISIBLE);
            recyclerView.getChildAt(index).findViewById(R.id.remove).setVisibility(View.GONE);
        }
    }


    public static void sePlaybackSong(Song song) throws Exception {
        player.reset();
        player.setDataSource(context, Uri.parse(song.getUrl()));
        player.prepareAsync();
        TextView artist = HomeFragment.getInstance().getActivity().findViewById(R.id.playingArtist);
        TextView title = HomeFragment.getInstance().getActivity().findViewById(R.id.playingTitle);
        artist.setText(song.getArtist());
        title.setText(song.getArtist());
        HomeFragment.getInstance().getActivity().findViewById(R.id.play).setVisibility(View.GONE);
        HomeFragment.getInstance().getActivity().findViewById(R.id.loadingPlayback).setVisibility(View.VISIBLE);

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                HomeFragment.getInstance().getActivity().findViewById(R.id.loadingPlayback).setVisibility(View.GONE);
                HomeFragment.getInstance().getActivity().findViewById(R.id.play).setVisibility(View.GONE);
                HomeFragment.getInstance().getActivity().findViewById(R.id.pause).setVisibility(View.VISIBLE);
                player.start();
            }
        });
    }

    public static void setContext(Context context1) {
        context = context1;
    }

    public static void initializePlayer() {
        player = new MediaPlayer();
        player.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());
    }

    public static void resetPlayer() {
        player.reset();
        player = null;
    }


    public static <T> void setFetchedVideos(List<T> page) {
        for (int i = 0; i < page.size(); i++) {
            List<String> extractedValues = Utils.extractValuesFromString(page.get(i).toString());
            try {
                fetchedVideos.add(new Song(extractedValues.get(2), extractedValues.get(0), extractedValues.get(3), extractedValues.get(1)));
            } catch (Exception e) {
                return;
            }
        }
    }


}
