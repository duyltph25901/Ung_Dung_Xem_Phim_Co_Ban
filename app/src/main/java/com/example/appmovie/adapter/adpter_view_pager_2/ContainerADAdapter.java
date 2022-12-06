package com.example.appmovie.adapter.adpter_view_pager_2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmovie.view.view_ad.fragment.ActorsFragment;
import com.example.appmovie.view.view_ad.fragment.DirectorsFragment;
import com.example.appmovie.view.view_ad.fragment.HomeAdFragment;
import com.example.appmovie.view.view_ad.fragment.MoviesFragment;

public class ContainerADAdapter extends FragmentStateAdapter {
    public ContainerADAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new HomeAdFragment();
            } case 1: {
                return new DirectorsFragment();
            } case 2: {
                return new ActorsFragment();
            } case 3: {
                return new MoviesFragment();
            } default: {
                return new HomeAdFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
