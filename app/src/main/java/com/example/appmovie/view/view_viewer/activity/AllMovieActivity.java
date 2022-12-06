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

public class AllMovieActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private EditText inputSearchMovieName;
    private ImageView imgSearch;
    private RecyclerView rcvMovie;
    private LottieAnimationView lottieAnimationView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MovieListAdapter adapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movie);

        init();
        setEvent();
    }

    private void init() {
        inputSearchMovieName = findViewById(R.id.inputSearchMovieByName);
        imgSearch = findViewById(R.id.imgSearchMovie);
        rcvMovie = findViewById(R.id.rcvShowAllMovie);
        lottieAnimationView = findViewById(R.id.imageNoDataAll);
        swipeRefreshLayout = findViewById(R.id.refreshAll);

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
        rcvMovie.setLayoutManager(gridLayoutManager);
        rcvMovie.setAdapter(adapter);
    }

    private void loadData() {
        movies = MovieDatabase.getInstance(this).movieDAO().getMovie();
        adapter.setMovies(movies);
    }

    private void setEvent() {
        imgSearch.setOnClickListener(v -> search());
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void search() {
        displayKeyBoard();
        String key = inputSearchMovieName.getText().toString().trim();

        if (key.isEmpty()) {
            movies = new ArrayList<>();
            movies = MovieDatabase.getInstance(this).movieDAO().getMovie();
            lottieAnimationView.setVisibility(View.GONE);
            rcvMovie.setVisibility(View.VISIBLE);
            adapter.setMovies(movies);
        } else {
            movies = new ArrayList<>();
            movies = MovieDatabase.getInstance(this).movieDAO().filterMovieByName(key);
            if (movies.size() == 0) {
                rcvMovie.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                return;
            }
            lottieAnimationView.setVisibility(View.GONE);
            rcvMovie.setVisibility(View.VISIBLE);
            adapter.setMovies(movies);
        }
    }

    private void displayKeyBoard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(AllMovieActivity.this.INPUT_METHOD_SERVICE);
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
            rcvMovie.setAdapter(adapter);
            rcvMovie.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
            inputSearchMovieName.setText("");
            displayKeyBoard();
            swipeRefreshLayout.setRefreshing(false);
//            Toast.makeText(this, "Data refresh successful!", Toast.LENGTH_SHORT).show();
        }, 1000);
    }
}