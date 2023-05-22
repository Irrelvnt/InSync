package com.irrelvnt.nsync.ui;

import android.annotation.SuppressLint;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.R;
import com.irrelvnt.nsync.databinding.ActivityMainBinding;
import com.irrelvnt.nsync.ui.song.Song;
import com.irrelvnt.nsync.utils.SaveAndLoad;

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
        Player.setPlayPauseButtons(findViewById(R.id.play), findViewById(R.id.pause));
        Player.setContext(this);
        Player.initializePlayer();
        SaveAndLoad saveAndLoad = new SaveAndLoad(getApplicationContext(), Player.nowPlaying);
        try {
            List<Song> loadedSongs = saveAndLoad.load();
            if (loadedSongs.size() > 0) {
                Player.selectedSongs = loadedSongs;
                Player.nowPlaying.addAll(loadedSongs);
                findViewById(R.id.songsRecyclerView).setVisibility(View.VISIBLE);
                findViewById(R.id.nothing).setVisibility(View.GONE);
            } else {
                findViewById(R.id.songsRecyclerView).setVisibility(View.GONE);
                findViewById(R.id.nothing).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("TAG", "errorrrr", e);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Player.resetPlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void goToDiscover(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.nav_discover);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}