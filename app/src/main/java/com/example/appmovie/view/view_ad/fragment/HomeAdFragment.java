package com.example.appmovie.view.view_ad.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appmovie.R;
import com.example.appmovie.view.view_ad.activities.ActorsActivity;
import com.example.appmovie.view.view_ad.activities.DirectorsActivity;
import com.example.appmovie.view.view_ad.activities.MovieActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeAdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeAdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeAdFragment newInstance(String param1, String param2) {
        HomeAdFragment fragment = new HomeAdFragment();
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

    private Button btnDirectors, btnActors, btnMovies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_ad, container, false);

        init(view);
        setEvent();

        return view;
    }

    private void init(View view) {
        btnDirectors = view.findViewById(R.id.btnDirectorsList);
        btnActors = view.findViewById(R.id.btnActorsList);
        btnMovies = view.findViewById(R.id.btnMoviesList);
    }

    private void setEvent() {
        btnMovies.setOnClickListener(v -> moviesList());
        btnDirectors.setOnClickListener(v -> directorsList());
        btnActors.setOnClickListener(v -> actorsList());
    }

    private void moviesList() {
        startActivity(new Intent(getActivity().getBaseContext(), MovieActivity.class));
    }

    private void directorsList() {
        startActivity(new Intent(getActivity().getBaseContext(), DirectorsActivity.class));
    }

    private void actorsList() {
        startActivity(new Intent(getActivity().getBaseContext(), ActorsActivity.class));
    }
}