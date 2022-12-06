package com.example.appmovie.adapter.adpter_view_pager_2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmovie.view.view_viewer.fragment_tab_layout.ComedyMovieFragment;
import com.example.appmovie.view.view_viewer.fragment_tab_layout.MoreFragment;
import com.example.appmovie.view.view_viewer.fragment_tab_layout.RomanceMovieFragment;

public class ContainerMovieCategoryAdapter extends FragmentStateAdapter {
    public ContainerMovieCategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new ComedyMovieFragment();
            } case 1: {
                return new RomanceMovieFragment();
            } case 2: {
                return new MoreFragment();
            } default: {
                return new ComedyMovieFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
