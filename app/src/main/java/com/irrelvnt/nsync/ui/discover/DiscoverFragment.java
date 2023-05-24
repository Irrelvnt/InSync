package com.irrelvnt.nsync.ui.discover;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.MusicExtractor.MusicProvider;
import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.databinding.FragmentDiscoverBinding;
import com.irrelvnt.nsync.ui.song.SongAdapter;
import com.irrelvnt.nsync.utils.SessionSaver;

public class DiscoverFragment extends Fragment {

    private static DiscoverFragment instance;

    private FragmentDiscoverBinding binding;
    private RelativeLayout fetchedView;
    private RelativeLayout nothingToShow;
    private RelativeLayout loading;
    private RecyclerView recyclerView;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instance = this;

        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fetchedView = binding.fetched;
        recyclerView = binding.recyclerView;
        nothingToShow = binding.nothing;
        loading = binding.loadingFetch;
        recyclerView.setAdapter(new SongAdapter(Player.fetchedVideos, song -> {
            int index = Player.fetchedVideos.indexOf(song);
            Player.selectSong(song, recyclerView, index);
        }));
        MusicProvider.setRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Activity mainActivity = getActivity();
        InputMethodManager inputMethodManager = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        binding.searchquery.setOnTouchListener(
                (v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        binding.addToNowPlaying.setVisibility(View.GONE);
                        int drawableRightWidth = binding.searchquery.getCompoundDrawables()[2].getBounds().width();
                        if (event.getRawX() >= (binding.searchquery.getRight() - drawableRightWidth)) {
                            binding.nothing.setVisibility(View.GONE);
                            binding.loadingFetch.setVisibility(View.VISIBLE);
                            MusicProvider.getInfoFromName(binding.searchquery.getText().toString());
                            binding.addToNowPlaying.setVisibility(View.VISIBLE);
                            View view = mainActivity.getCurrentFocus();
                            if (view != null) {
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            return true;
                        }
                    }
                    return false;
                });
        binding.addToNowPlaying.setOnClickListener(v -> {
            if (Player.selectedSongs.size() > 0) {
                Player.nowPlaying.addAll(Player.selectedSongs);
                new SessionSaver(Player.nowPlaying, requireContext().getApplicationContext()).saveNow();
            }
        });
        return root;
    }

    public void changeVisibility() {
        if (Player.fetchedVideos.size() != 0) {
            nothingToShow.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            fetchedView.setVisibility(View.VISIBLE);
        }
    }

    public static DiscoverFragment getInstance() {
        return instance;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}