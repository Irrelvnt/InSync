package com.irrelvnt.nsync.ui;

import android.annotation.SuppressLint;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.irrelvnt.nsync.MusicExtractor.YoutubeExtractor;
import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.R;
import com.irrelvnt.nsync.databinding.ActivityMainBinding;
import com.irrelvnt.nsync.ui.song.Song;
import com.irrelvnt.nsync.ui.song.SongAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Create playlist", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_discover)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ImageView backgroundeffect = binding.backgroundimg;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            backgroundeffect.setRenderEffect(RenderEffect.createBlurEffect(100, 100, Shader.TileMode.MIRROR));
        }

        //songs recyclerView
        List<Song> playingNow = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.songsRecyclerView);
        RelativeLayout nothingToShow = findViewById(R.id.nothing);
        if (!playingNow.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new SongAdapter(getApplicationContext(), playingNow));
            recyclerView.setVisibility(View.VISIBLE);
            nothingToShow.setVisibility((View.GONE));
        }
        Player.setPlayPauseButtons(findViewById(R.id.play), findViewById(R.id.pause));
        Player.initializePlayer();
        try {
            Player.setUrl("https://music.songsio.com/a/latenightthghts/02%20late%20night%20thoughts.mp3");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        YoutubeExtractor.getSongTask("https://www.youtube.com/watch?v=jH1RNk8954Q");
        YoutubeExtractor.getInfoFromName("hello");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Player.releasePlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}