package com.irrelvnt.nsync.ui.discover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class DiscoverFragment extends Fragment {

    private static DiscoverFragment instance;

    private FragmentDiscoverBinding binding;
    private RelativeLayout fetchedView;
    private RelativeLayout nothingToShow;
    private SongAdapter adapter;
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
        adapter = new SongAdapter(Player.fetchedVideos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.searchquery.setOnTouchListener(
                (v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        int drawableRightWidth = binding.searchquery.getCompoundDrawables()[2].getBounds().width();
                        if (event.getRawX() >= (binding.searchquery.getRight() - drawableRightWidth)) {
                            MusicProvider.getInfoFromName(binding.searchquery.getText().toString());
                            Toast.makeText(requireContext(), "searching", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    }
                    return false;
                });
        return root;
    }

    public void changeVisibility() {
        if (fetchedView.getVisibility() == View.VISIBLE) {
            fetchedView.setVisibility(View.GONE);
            nothingToShow.setVisibility(View.VISIBLE);
        } else {
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