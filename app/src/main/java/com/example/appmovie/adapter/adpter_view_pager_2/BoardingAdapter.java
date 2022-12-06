package com.example.appmovie.adapter.adpter_view_pager_2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmovie.boarding.fragment_boarding.BoardingOneFragment;
import com.example.appmovie.boarding.fragment_boarding.BoardingTwoFragment;
import com.example.appmovie.boarding.fragment_boarding.OnBoardingThreeFragment;

public class BoardingAdapter extends FragmentStateAdapter {
    public BoardingAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new BoardingOneFragment();
            } case 1: {
                return new BoardingTwoFragment();
            } case 2: {
                return new OnBoardingThreeFragment();
            } default: {
                return new BoardingOneFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
