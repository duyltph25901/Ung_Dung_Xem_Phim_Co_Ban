package com.example.appmovie.view.view_viewer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adpter_view_pager_2.ContainerMovieCategoryAdapter;
import com.example.appmovie.class_supported.viewpager_motion_effect.DepthPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesViewerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesViewerFragment newInstance(String param1, String param2) {
        CategoriesViewerFragment fragment = new CategoriesViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TabLayout tabLayout;
    private ViewPager2 layoutContent;
    private ContainerMovieCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        layoutContent = view.findViewById(R.id.layoutContentMovieMore);

        adapter = new ContainerMovieCategoryAdapter(getActivity());
        layoutContent.setAdapter(adapter);
        layoutContent.setPageTransformer(new DepthPageTransformer());
        new TabLayoutMediator(tabLayout, layoutContent, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText("Comedy");
                    break;
                } case 1: {
                    tab.setText("Romance");
                    break;
                } case 2: {
                    tab.setText("More");
                    break;
                }
            }
        }).attach();
    }
}