package com.irrelvnt.nsync;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.irrelvnt.nsync.ui.song.Song;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SaveAndLoad {
    SharedPreferences sharedPreferences;
    ByteArrayInputStream byteArrayInputStream;
    List<Song> songs;
    SharedPreferences.Editor editor;
    ByteArrayOutputStream byteArrayOuputStream;

    public SaveAndLoad(Context context, List<Song> songs) {
        sharedPreferences = context.getSharedPreferences("NowPlaying", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.songs = songs;
    }

    public void save() throws IOException, ClassNotFoundException {
        load();
        byteArrayOuputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOuputStream);
        objectOutputStream.writeObject(songs);
        objectOutputStream.flush();
        byte[] serializedObject = byteArrayOuputStream.toByteArray();
        String encodedObject = Base64.encodeToString(serializedObject, Base64.DEFAULT);
        editor.putString("NowPlaying", encodedObject);
        editor.apply();
    }

    public List<Song> load() throws IOException, ClassNotFoundException {
        String encodedObject = sharedPreferences.getString("NowPlaying", null);
        byte[] serializedObject = Base64.decode(encodedObject, Base64.DEFAULT);
        byteArrayInputStream = new ByteArrayInputStream(serializedObject);
        ObjectInputStream objectInputStream;
        objectInputStream = new ObjectInputStream(SaveAndLoad.this.byteArrayInputStream);
        List<Song> loadedSongs = (List<Song>) objectInputStream.readObject();
        if (loadedSongs != null) {
            songs.addAll(loadedSongs);
            return songs;
        } else {
            return null;
        }
    }
}
