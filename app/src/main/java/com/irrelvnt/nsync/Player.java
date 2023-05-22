package com.irrelvnt.nsync;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.ui.song.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Player {
    private static MediaPlayer player;
    public static List<Song> nowPlaying = new ArrayList<>();
    public static List<Song> selectedSongs = new ArrayList<>();
    private static Song playing;
    public static List<Song> fetchedVideos = new ArrayList<>();

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

    public static void addToNowPlaying() {
        nowPlaying.addAll(selectedSongs);
        selectedSongs.clear();
        playing = nowPlaying.get(nowPlaying.size() - 1);
        try {
            setUrl(playing.getUrl());
        } catch (Exception e) {
        }
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


    public static void setUrl(String url) throws IOException {
        player.setDataSource(url);
        player.prepare();
    }

    public static void initializePlayer() {
        player = new MediaPlayer();
        player.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());
    }

    public static void releasePlayer() {
        player.release();
        player = null;
    }


    public static <T> void setFetchedVideos(List<T> page) {
        for (int i = 0; i < page.size(); i++) {
            List<String> extractedValues = Utils.extractValuesFromString(page.get(i).toString());
            Log.e("TAG", extractedValues.toString());
            try {
                fetchedVideos.add(new Song(extractedValues.get(2), extractedValues.get(0), extractedValues.get(3), extractedValues.get(1)));
            } catch (Exception e) {
                return;
            }
        }
    }


}
