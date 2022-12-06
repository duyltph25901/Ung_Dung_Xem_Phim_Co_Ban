package com.example.appmovie.view.view_viewer.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieFavoriteViewerHomeAdapter;
import com.example.appmovie.db.db_movie_favorite.MovieFavoriteDatabase;
import com.example.appmovie.model.MovieFavorite;
import com.example.appmovie.view.view_viewer.activity.AllMovieActivity;
import com.example.appmovie.view.view_viewer.activity.ExpandActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteViewerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteViewerFragment newInstance(String param1, String param2) {
        FavoriteViewerFragment fragment = new FavoriteViewerFragment();
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
    private ImageView imgSearch;
    private EditText inputSearchMovie;
    private LottieAnimationView lottieAnimationView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<MovieFavorite> movies;
    private MovieFavoriteViewerHomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_viewer, container, false);

        init(view);
        imgSearch.setOnClickListener(v -> search());
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    private void init(View view) {
        rcv = view.findViewById(R.id.rcvShowFavoriteMovie);
        imgSearch = view.findViewById(R.id.imgSearchMovieFavorite);
        inputSearchMovie = view.findViewById(R.id.inputSearchMovieByNameFavoriteMovie);
        lottieAnimationView = view.findViewById(R.id.imageNoData);
        swipeRefreshLayout = view.findViewById(R.id.refreshFavorite);

        movies = new ArrayList<>();
        adapter = new MovieFavoriteViewerHomeAdapter(movie -> {
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MOVIE", movie.getMovieName());
            Intent intent = new Intent(getActivity().getBaseContext(), ExpandActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        adapter.setMovies(movies);

        loadData();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setAdapter(adapter);
    }

    private void loadData() {
        movies = MovieFavoriteDatabase.getInstance(getActivity()).movieFavoriteDAO().getMoviesFavorite();
        adapter.setMovies(movies);
    }

    private void search() {
        displayKeyBoard();

        String key = inputSearchMovie.getText().toString().trim();
        if (key.isEmpty()) {
            movies = new ArrayList<>();
            loadData();
            lottieAnimationView.setVisibility(View.GONE);
            rcv.setVisibility(View.VISIBLE);
        } else {
            movies = new ArrayList<>();
            movies = MovieFavoriteDatabase.getInstance(getActivity()).movieFavoriteDAO().filterListMovieFavoriteByName(key);

            if (movies.size() == 0) {
                rcv.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
            } else {
                lottieAnimationView.setVisibility(View.GONE);
                adapter.setMovies(movies);
                rcv.setVisibility(View.VISIBLE);
            }
        }
    }

    private void displayKeyBoard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            loadData();
            rcv.setAdapter(adapter);
            rcv.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
            inputSearchMovie.setText("");
            displayKeyBoard();
            swipeRefreshLayout.setRefreshing(false);
//            Toast.makeText(this, "Data refresh successful!", Toast.LENGTH_SHORT).show();
        }, 1000);
    }
}