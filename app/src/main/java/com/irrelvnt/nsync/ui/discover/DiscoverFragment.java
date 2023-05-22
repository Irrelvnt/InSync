package com.irrelvnt.nsync.ui.discover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.irrelvnt.nsync.MusicExtractor.YoutubeExtractor;
import com.irrelvnt.nsync.databinding.FragmentDiscoverBinding;

public class DiscoverFragment extends Fragment {

    private FragmentDiscoverBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.searchquery.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            int drawableRightWidth = binding.searchquery.getCompoundDrawables()[2].getBounds().width();
                            if (event.getRawX() >= (binding.searchquery.getRight() - drawableRightWidth)) {
                                YoutubeExtractor.getInfoFromName(binding.searchquery.getText().toString());
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}