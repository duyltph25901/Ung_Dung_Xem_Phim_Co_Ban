package com.example.appmovie.view.view_viewer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieListAdapter;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class HorrorActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView rcv;
    private ImageView imgSearch;
    private EditText inputSearchMovie;
    private LottieAnimationView lottieAnimationView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Movie> movies;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horror);

        init();

        imgSearch.setOnClickListener(v -> search());
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void init() {
        rcv = findViewById(R.id.rcvShowHorrorMovie);
        imgSearch = findViewById(R.id.imgSearchMovieHorror);
        inputSearchMovie = findViewById(R.id.inputSearchMovieByNameHorrorMovie);
        lottieAnimationView = findViewById(R.id.imageNoDataHorror);
        swipeRefreshLayout = findViewById(R.id.refreshHorror);

        movies = new ArrayList<>();
        adapter = new MovieListAdapter(movie -> {
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MOVIE", movie.getMovieName());
            Intent intent = new Intent(getBaseContext(), ExpandActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        adapter.setMovies(movies);

        loadData();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setAdapter(adapter);
    }

    private void loadData() {
        movies = MovieDatabase.getInstance(this).movieDAO().filterMovieByCategory("Horror Movies");
        adapter.setMovies(movies);
    }

    private void search() {
        displayKeyBoard();

        String key = inputSearchMovie.getText().toString().trim();
        if (key.isEmpty()) {
            movies = new ArrayList<>();
            loadData();
            rcv.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
        } else {
            movies = new ArrayList<>();
            movies = MovieDatabase.getInstance(this).movieDAO().filterMovieByCategoryAndName("Horror Movies", key);
            adapter.setMovies(movies);

            if (movies.size() == 0  || movies.isEmpty()) {
                rcv.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
            } else {
                rcv.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                rcv.setAdapter(adapter);
            }
        }
    }

    private void displayKeyBoard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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