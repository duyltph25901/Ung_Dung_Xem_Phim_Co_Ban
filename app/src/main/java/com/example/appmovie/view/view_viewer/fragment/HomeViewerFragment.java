package com.example.appmovie.view.view_viewer.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieViewerHomeAdapter;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Movie;
import com.example.appmovie.view.view_viewer.activity.ActionActivity;
import com.example.appmovie.view.view_viewer.activity.AnimatedActivity;
import com.example.appmovie.view.view_viewer.activity.ExpandActivity;
import com.example.appmovie.view.view_viewer.activity.AllMovieActivity;
import com.example.appmovie.view.view_viewer.activity.HorrorActivity;
import com.example.appmovie.view.view_viewer.activity.SciActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeViewerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeViewerFragment newInstance(String param1, String param2) {
        HomeViewerFragment fragment = new HomeViewerFragment();
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

    private RecyclerView rcvHit, rcvAction , rcvSci, rcvHorror, rcvAnimated;
    private TextView txtSeeAllHit, txtSeeAllAction, txtSeeAllSci, txtSeeAllHorror, txtSeeAllAnimated;

    private List<Movie> moviesHit;
    private List<Movie> moviesAction;
    private List<Movie> moviesSci;
    private List<Movie> moviesHorror;
    private List<Movie> moviesAnimated;
    private MovieViewerHomeAdapter adapterHit, adapterAction, adapterSci, adapterHorror, adapterAnimated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_viewer, container, false);

        init(view);
        setEvent();

        return view;
    }

    private void init(final View view) {
        rcvHit = view.findViewById(R.id.rcvMovieHitHome);
        rcvAction = view.findViewById(R.id.rcvActionMovieHome);
        rcvSci = view.findViewById(R.id.rcvSciMovieHome);
        rcvHorror = view.findViewById(R.id.rcvHorrorMovieHome);
        txtSeeAllHit = view.findViewById(R.id.txtSeeAllHitMovie);
        txtSeeAllAction = view.findViewById(R.id.txtSeeAllActionMovie);
        txtSeeAllSci = view.findViewById(R.id.txtSeeAllSciMovie);
        txtSeeAllHorror = view.findViewById(R.id.txtSeeAllHorrorMovie);
        txtSeeAllAnimated = view.findViewById(R.id.txtSeeAllAnimatedMovie);
        rcvAnimated = view.findViewById(R.id.rcvAnimatedMovieHome);

        moviesHit = new ArrayList<>();
        moviesAction = new ArrayList<>();
        moviesSci = new ArrayList<>();
        moviesHorror = new ArrayList<>();

        getData();
        setAdapter();
    }

    private void getData() {
        moviesHit = getMoviesHit();
        moviesAction = getMovieByCategory("Action Movies");
        moviesHorror = getMovieByCategory("Horror Movies");
        moviesSci = getMovieByCategory("Sci-fi Movies");
        moviesAnimated = getMovieByCategory("Animated Movies");
    }

    private List<Movie> getMoviesHit() {
        List<Movie> list = new ArrayList<>();

        list = MovieDatabase.getInstance(getActivity()).movieDAO().getMovie();

        return list;
    }

    private List<Movie> getMovieByCategory(String category) {
        List<Movie> list = new ArrayList<>();

        list = filterMovieByCategory(category);

        return  list;
    }

    private List<Movie> filterMovieByCategory(String category) {
        List<Movie> list = new ArrayList<>();

        list = MovieDatabase.getInstance(getActivity())
                .movieDAO().filterMovieByCategory(category);

        return list;
    }

    private void setAdapter() {
        // Lay 7 phim de xuat
        List<Movie> moviesHit2 = new ArrayList<>();
        if (moviesHit.size() >= 7) {
            for (int i=0; i<7; i++) {
                moviesHit2.add(moviesHit.get(i));
            }
        } else {
            for (int i=0; i<moviesHit.size(); i++) {
                moviesHit2.add(moviesHit.get(i));
            }
        }

        // Lay 5 phim action de xuat
        List<Movie> movieAction2 = new ArrayList<>();
        if (moviesAction.size() >= 5) {
            for (int i=0; i<5; i++) {
                movieAction2.add(moviesAction.get(i));
            }
        } else {
            for (int i=0; i<moviesAction.size(); i++) {
                movieAction2.add(moviesAction.get(i));
            }
        }

        // Lay 5 phim sci de xuat
        List<Movie> moviesSci2 = new ArrayList<>();
        if (moviesSci.size() >= 5) {
            for (int i=0; i<5; i++) {
                moviesSci2.add(moviesSci.get(i));
            }
        } else {
            for (int i=0; i<moviesSci.size(); i++) {
                moviesSci2.add(moviesSci.get(i));
            }
        }

        // Lay 5 phim horror
        List<Movie> moviesHorror2 = new ArrayList<>();
        if (moviesHorror.size() >= 5) {
            for (int i=0; i<5; i++) {
                moviesHorror2.add(moviesHorror.get(i));
            }
        } else {
            for (int i=0; i<moviesHorror.size(); i++) {
                moviesHorror2.add(moviesHorror.get(i));
            }
        }

        // Lay 5 phim hoat hinh
        List<Movie> moviesAnimated2 = new ArrayList<>();
        if (moviesAnimated.size() >= 5) {
            for (int i=0; i<5; i++) {
                moviesAnimated2.add(moviesAnimated.get(i));
            }
        } else {
            for (int i=0; i<moviesAnimated.size(); i++) {
                moviesAnimated2.add(moviesAnimated.get(i));
            }
        }
        setAdapterRcv(adapterHit, moviesHit2, rcvHit);
        setAdapterRcv(adapterAction, movieAction2, rcvAction);
        setAdapterRcv(adapterSci, moviesSci2, rcvSci);
        setAdapterRcv(adapterHorror, moviesHorror2, rcvHorror);
        setAdapterRcv(adapterAnimated, moviesAnimated2, rcvAnimated);
    }

    private void setAdapterRcv(MovieViewerHomeAdapter adapter, List<Movie> list, RecyclerView rcv) {
        adapter = new MovieViewerHomeAdapter(movie -> {
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MOVIE", movie.getMovieName());
            Intent intent = new Intent(getActivity().getBaseContext(), ExpandActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        adapter.setMovies(list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }

    private void setEvent() {
        txtSeeAllHit.setOnClickListener(v -> startActivity(new Intent(getActivity().getBaseContext(), AllMovieActivity.class)));
        txtSeeAllAction.setOnClickListener(v -> startActivity(new Intent(getActivity().getBaseContext(), ActionActivity.class)));
        txtSeeAllSci.setOnClickListener(v -> startActivity(new Intent(getActivity().getBaseContext(), SciActivity.class)));
        txtSeeAllHorror.setOnClickListener(v -> startActivity(new Intent(getActivity().getBaseContext(), HorrorActivity.class)));
        txtSeeAllAnimated.setOnClickListener(v -> startActivity(new Intent(getActivity().getBaseContext(), AnimatedActivity.class)));
    }
}