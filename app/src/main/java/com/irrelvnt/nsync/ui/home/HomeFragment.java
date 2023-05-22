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

import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.databinding.FragmentHomeBinding;
import com.irrelvnt.nsync.ui.song.SongAdapter;

public class HomeFragment extends Fragment {
    private static HomeFragment instance;
    public RecyclerView recyclerView;
    public RelativeLayout nothingToShow;
    private SongAdapter adapter;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instance = this;
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.songsRecyclerView;
        nothingToShow = binding.nothing;
        adapter = new SongAdapter(Player.nowPlaying);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return root;
    }

    public static HomeFragment getInstance() {
        return instance;
    }

    public void changeVisibility() {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            nothingToShow.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            nothingToShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}