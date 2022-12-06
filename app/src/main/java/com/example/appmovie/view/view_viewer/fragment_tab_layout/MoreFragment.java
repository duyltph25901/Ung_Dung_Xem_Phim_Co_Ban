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
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        rcv = view.findViewById(R.id.rcvMoreMovie);
        movies = new ArrayList<>();
        adapter = new MovieListAdapter(movie -> {
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MOVIE", movie.getMovieName());
            Intent intent = new Intent(getActivity().getBaseContext(), ExpandActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        adapter.setMovies(movies);

        for (int i=0; i<getWarMovies().size() ; i++) {
            movies.add(getWarMovies().get(i));
        } for (int i=0; i<getDramaFilm().size(); i++) {
            movies.add(getDramaFilm().get(i));
        } for (int i=0; i<getMusicalMovie().size(); i++) {
            movies.add(getMusicalMovie().get(i));
        } for (int i=0; i<getFilmNoir().size(); i++) {
            movies.add(getFilmNoir().get(i));
        } for (int i=0; i<getMovieCrime().size(); i++) {
            movies.add(getMovieCrime().get(i));
        }

        adapter.setMovies(movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setAdapter(adapter);

        return view;
    }

    private List<Movie> getWarMovies() {
        List<Movie> movies = new ArrayList<>();

        movies = MovieDatabase.getInstance(getActivity()).movieDAO().filterMovieByCategory("War Movies");

        return movies;
    }

    private List<Movie> getMusicalMovie() {
        List<Movie> movies = new ArrayList<>();
        movies = MovieDatabase.getInstance(getActivity()).movieDAO().filterMovieByCategory("Musical Film");
        return movies;
    }

    private List<Movie> getFilmNoir() {
        List<Movie> movies = new ArrayList<>();
        movies = MovieDatabase.getInstance(getActivity()).movieDAO().filterMovieByCategory("Film Noir");
        return movies;
    }

    private List<Movie> getDramaFilm() {
        List<Movie> movies = new ArrayList<>();
        movies = MovieDatabase.getInstance(getActivity()).movieDAO().filterMovieByCategory("Drama Movies");
        return movies;
    }

    private List<Movie> getMovieCrime() {
        List<Movie> movies = new ArrayList<>();
        movies = MovieDatabase.getInstance(getActivity()).movieDAO().filterMovieByCategory("Crime Movies");
        return movies;
    }
}