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
import com.irrelvnt.nsync.clickListener.OnItemClickListener;
import com.irrelvnt.nsync.databinding.FragmentDiscoverBinding;
import com.irrelvnt.nsync.ui.song.Song;
import com.irrelvnt.nsync.ui.songList.SongAdapter;

public class DiscoverFragment extends Fragment {

    private static DiscoverFragment instance;

    private FragmentDiscoverBinding binding;
    private RelativeLayout fetchedView;
    private RelativeLayout nothingToShow;
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
        recyclerView.setAdapter(new SongAdapter(Player.fetchedVideos, new OnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                int index = Player.fetchedVideos.indexOf(song);
                Player.selectSong(song, recyclerView, index);
            }
        }));
        MusicProvider.setRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Activity mainActivity = getActivity();
        InputMethodManager inputMethodManager = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        binding.searchquery.setOnTouchListener(
                (v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        int drawableRightWidth = binding.searchquery.getCompoundDrawables()[2].getBounds().width();
                        if (event.getRawX() >= (binding.searchquery.getRight() - drawableRightWidth)) {
                            MusicProvider.getInfoFromName(binding.searchquery.getText().toString());
                            Toast.makeText(requireContext(), "searching", Toast.LENGTH_LONG).show();
                            View view = mainActivity.getCurrentFocus();
                            if (view != null) {
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            return true;
                        }
                    }
                    return false;
                });
        binding.addToNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Player.selectedSongs.size() > 0) {
                    Player.addToNowPlaying();
                }
            }
        });
        return root;
    }

    public void changeVisibility() {
        if (Player.fetchedVideos.size() != 0) {
            fetchedView.setVisibility(View.VISIBLE);
            nothingToShow.setVisibility(View.GONE);
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