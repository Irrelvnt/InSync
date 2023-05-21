package com.irrelvnt.nsync;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;

import com.irrelvnt.nsync.ui.song.Song;

import java.io.IOException;
import java.util.List;

public class Player {

    public static Boolean isPlaying = false;
    private static MediaPlayer player;
    public List<Song> nowPlaying;
    public List<Song> selectedSongs;

    public static void setPlayPauseButtons(ImageButton play, ImageButton pause) {
        play.setOnClickListener(v -> {
            if (player == null) {
                initializePlayer();
            } else {
                player.start();
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                isPlaying = true;
            }
        });
        pause.setOnClickListener(v -> {
            if (player == null) {
                initializePlayer();
            } else {
                player.pause();
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                isPlaying = false;
            }
        });
    }

    public void selectSong(Song song) {
        if (!selectedSongs.contains(song)) {
            selectedSongs.add(song);
        }
    }

    public void unselectSong(Song song) {
        if (selectedSongs.contains(song)) {
            selectedSongs.remove(song);
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
}
