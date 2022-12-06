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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieListAdapter;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class ActionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
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
        setContentView(R.layout.activity_action);

        init();
        setEvent();
    }

    private void init() {
        rcv = findViewById(R.id.rcvShowActionMovie);
        imgSearch = findViewById(R.id.imgSearchMovieAction);
        inputSearchMovie = findViewById(R.id.inputSearchMovieByNameActionMovie);
        lottieAnimationView = findViewById(R.id.imageNoDataAction);
        swipeRefreshLayout = findViewById(R.id.refreshAction);

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
        movies = MovieDatabase.getInstance(this)
                .movieDAO().filterMovieByCategory("Action Movies");
        adapter.setMovies(movies);
    }

    private void setEvent() {
        imgSearch.setOnClickListener(v -> search());
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void search() {
        displayKeyBoard();

        String key = inputSearchMovie.getText().toString().trim();
        if (key.isEmpty()) {
            movies = new ArrayList<>();
            movies = MovieDatabase.getInstance(this).movieDAO().filterMovieByCategory("Action Movies");
            adapter.setMovies(movies);

            rcv.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
        } else {
            movies = new ArrayList<>();
            movies = MovieDatabase.getInstance(this).movieDAO().filterMovieByCategoryAndName("Action Movies", key);
            if (movies.size() == 0) {
                rcv.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                return;
            }

            rcv.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
            adapter.setMovies(movies);
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