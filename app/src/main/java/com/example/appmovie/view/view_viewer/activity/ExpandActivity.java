package com.example.appmovie.view.view_viewer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.ActorViewerAdapter;
import com.example.appmovie.broad_cast.MyBroadCast;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.db.db_actor.ActorDatabase;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.db.db_movie_favorite.MovieFavoriteDatabase;
import com.example.appmovie.model.Actor;
import com.example.appmovie.model.Movie;
import com.example.appmovie.model.MovieFavorite;

import java.util.ArrayList;
import java.util.List;

public class ExpandActivity extends AppCompatActivity {
    private TextView txtPoint, txtMovieName, txtYear;
    private TextView txtTime, txtCategory, txtContent;
    private RecyclerView rcvCast;
    private Button btnWatchTrailer, btnWatchMovie;
    private ImageView imgFavorite;
    private ImageView poster;
    private ImageView imgBack;

    private List<Actor> listActor;
    private ActorViewerAdapter adapter;
    private MyBroadCast myBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        savedInstanceState = this.getIntent().getExtras();
        String getKeyData = "";
        if (savedInstanceState != null) {
            getKeyData = (String) savedInstanceState.get("KEY_MOVIE");
        } if (getKeyData.isEmpty()) return;

        Movie movie = MovieDatabase.getInstance(ExpandActivity.this)
                .movieDAO().getMovieByName(getKeyData);
        if (movie == null) return;

        myBroadCast = new MyBroadCast();

        init();
        setData(movie);
        getActorFormMovie(movie);
        setEvent(movie);
    }

    private void init() {
        txtPoint = findViewById(R.id.txtPointMovieExpand);
        txtMovieName = findViewById(R.id.txtMovieNameExpand);
        txtYear = findViewById(R.id.txtYearOfMovie);
        txtTime = findViewById(R.id.txttTimeMovieExpand);
        txtCategory = findViewById(R.id.txtCategoryMovieExpand);
        txtContent = findViewById(R.id.txtSummaryMovieExpand);
        rcvCast = findViewById(R.id.rcvCastMovie);
        btnWatchMovie = findViewById(R.id.btnWatchMovie);
        btnWatchTrailer = findViewById(R.id.btnWatchTrailer);
        imgFavorite = findViewById(R.id.imgFavoriteMovie);
        poster = findViewById(R.id.posterExpand);
        imgBack = findViewById(R.id.imgClearExpandMovie);

        listActor = new ArrayList<>();
        adapter = new ActorViewerAdapter(actor -> {
            Bundle bundle = new Bundle();
            bundle.putString("KEY_ACTOR", actor.getFullName());
            Intent intent = new Intent(ExpandActivity.this, ActorActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        adapter.setActors(listActor);
    }

    private void setData(final Movie movie) {
        poster.setImageBitmap(
                ConvertImageStore.convertByArrToImage(movie.getPoster())
        );
        txtPoint.setText(movie.getPoint()+" (IMDb)");
        txtMovieName.setText(movie.getMovieName());
        txtTime.setText(movie.getTime()+" m");
        txtCategory.setText(movie.getCategory());
        txtContent.setText(movie.getSummary());

        MovieFavorite movieFavorite = MovieFavoriteDatabase.getInstance(this).movieFavoriteDAO().filterMovieFavoriteByName(movie.getMovieName());
        if (movieFavorite == null) { // Chua co trong danh sach yeu thich
            imgFavorite.setImageResource(R.drawable.ic_favorite_null);
        } else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_full);
        }

        String[] date = movie.getPremiereSchedule().split("/");
        String year = date[2];
        txtYear.setText("( "+year+" )");
    }

    private void getActorFormMovie(final Movie movie) {
        String actors = movie.getActors();
        String[] arr = actors.split(",");
        for (int i=0; i<arr.length; i++) {
            arr[i] = arr[i].trim();
        }

        for (int i=0; i<arr.length; i++) {
            String name = arr[i];
            if (name.isEmpty() || name.length() == 0) {
                ++i;
                continue;
            }

            Actor actor = ActorDatabase.getInstance(this).actorDAO().searchActorByName(name);
            if (actor == null) {
                ++i;
                continue;
            }

            listActor.add(actor);
        }

        adapter.setActors(listActor);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvCast.setLayoutManager(manager);
        rcvCast.setAdapter(adapter);
    }

    private void setEvent(final Movie movie) {
        btnWatchTrailer.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
            bundle.putString("KEY_LINK", movie.getLinkTrailer());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnWatchMovie.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
            bundle.putString("KEY_LINK", movie.getLinkFilm());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        imgBack.setOnClickListener(v -> onBackPressed());

        imgFavorite.setOnClickListener(v -> favorite(movie));
    }

    private void favorite(final Movie movie) {
        MovieFavorite movieFavorite = MovieFavoriteDatabase.getInstance(this).movieFavoriteDAO().filterMovieFavoriteByName(movie.getMovieName());
        if (movieFavorite ==  null) { // Chua co trong danh sach
            imgFavorite.setImageResource(R.drawable.ic_favorite_full);
            movieFavorite = new MovieFavorite(
                    movie.getPoster(), movie.getLinkTrailer(), movie.getLinkFilm(), movie.getMovieName(),
                    movie.getDirectors(), movie.getActors(), movie.getPremiereSchedule(), movie.getCategory(),
                    movie.getSummary(), movie.getTime(), movie.getLimitAge(), movie.getPoint()
            );
            MovieFavoriteDatabase.getInstance(this).movieFavoriteDAO().insert(movieFavorite);
        } else {
            MovieFavoriteDatabase.getInstance(this).movieFavoriteDAO().delete(movieFavorite);
            imgFavorite.setImageResource(R.drawable.ic_favorite_null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadCast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(myBroadCast);
    }
}