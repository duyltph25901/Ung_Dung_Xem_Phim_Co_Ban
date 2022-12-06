package com.example.appmovie.adapter.adpter_view_pager_2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmovie.view.view_viewer.fragment.CategoriesViewerFragment;
import com.example.appmovie.view.view_viewer.fragment.FavoriteViewerFragment;
import com.example.appmovie.view.view_viewer.fragment.HomeViewerFragment;

public class ContainerViewerAdapter extends FragmentStateAdapter {
    public ContainerViewerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new HomeViewerFragment();
            } case 1: {
                return new FavoriteViewerFragment();
            } case 2: {
                return new CategoriesViewerFragment();
            } default: {
                return new HomeViewerFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
