package com.irrelvnt.nsync.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.MusicExtractor.MusicProvider;
import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.clickListener.OnItemClickListener;
import com.irrelvnt.nsync.databinding.FragmentHomeBinding;
import com.irrelvnt.nsync.ui.song.Song;
import com.irrelvnt.nsync.ui.song.SongAdapter;

public class HomeFragment extends Fragment {
    private static HomeFragment instance;
    public RecyclerView recyclerView;
    public RelativeLayout nothingToShow;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instance = this;
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.songsRecyclerView;
        nothingToShow = binding.nothing;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new SongAdapter(Player.nowPlaying, new OnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                MusicProvider.getSongTask(song);
            }
        }));
        return root;
    }

    public static HomeFragment getInstance() {
        return instance;
    }


    public void changeVisibility() {
        if (Player.nowPlaying.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            nothingToShow.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            nothingToShow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}