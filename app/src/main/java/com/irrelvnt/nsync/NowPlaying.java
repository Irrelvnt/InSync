package com.irrelvnt.nsync;

import com.irrelvnt.nsync.ui.song.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class NowPlaying implements Serializable {
    public static List<Song> nowPlaying = new ArrayList<>();

}
