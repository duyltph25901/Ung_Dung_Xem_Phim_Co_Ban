package com.example.appmovie.view.view_viewer.fragment_tab_layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieListAdapter;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Movie;
import com.example.appmovie.view.view_viewer.activity.ExpandActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RomanceMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RomanceMovieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RomanceMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RomanceMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RomanceMovieFragment newInstance(String param1, String param2) {
        RomanceMovieFragment fragment = new RomanceMovieFragment();
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

    private RecyclerView rcv;
    private List<Movie> movies;
    private MovieListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_romance_movie, container, false);

        rcv = view.findViewById(R.id.rcvRomance);
        movies = new ArrayList<>();
        adapter = new MovieListAdapter(movie -> {
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MOVIE", movie.getMovieName());
            Intent intent = new Intent(getActivity().getBaseContext(), ExpandActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        movies = MovieDatabase.getInstance(getActivity()).movieDAO().filterMovieByCategory("Romance Movies");
        adapter.setMovies(movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setAdapter(adapter);

        return view;
    }
}