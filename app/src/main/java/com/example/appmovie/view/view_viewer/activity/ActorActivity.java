package com.example.appmovie.view.view_viewer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieViewerHomeAdapter;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.db.db_actor.ActorDatabase;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Actor;
import com.example.appmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class ActorActivity extends AppCompatActivity {
    private ImageView img;
    private TextView txtActorName, txtActorDOB, txtActorStory;
    private RecyclerView rcv;
    private LinearLayout layoutBack;

    private MovieViewerHomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        savedInstanceState = this.getIntent().getExtras();
        String getKeyData = "";
        if (savedInstanceState != null) {
            getKeyData = (String) savedInstanceState.get("KEY_ACTOR");
        } if (getKeyData.isEmpty()) return;

        Actor actor = ActorDatabase.getInstance(ActorActivity.this).actorDAO().searchActorByName(getKeyData);
        if (actor == null) return;

        init();
        setData(actor);
        layoutBack.setOnClickListener(v -> onBackPressed());
    }

    private void init() {
        img = findViewById(R.id.imageActorExpand);
        txtActorName = findViewById(R.id.txtActorNameExpand);
        txtActorDOB = findViewById(R.id.txtActorDOBExpand);
        txtActorStory = findViewById(R.id.txtActorStoryExpand);
        rcv = findViewById(R.id.rcvActorExpand);
        layoutBack = findViewById(R.id.layoutBack);

        adapter = new MovieViewerHomeAdapter(movie -> {
            return;
        });
    }

    private void setData(final Actor actor) {
        img.setImageBitmap(
                ConvertImageStore.convertByArrToImage(actor.getImg())
        );
        txtActorName.setText(actor.getFullName());
        txtActorDOB.setText(actor.getDOB());
        txtActorStory.setText(actor.getStory());

        setDataForRcv(actor);
    }

    private void setDataForRcv(final Actor actor) {
        List<Movie> movies = MovieDatabase.getInstance(ActorActivity.this).movieDAO().searchMovieByActorName(actor.getFullName());
        if (movies.size() == 0) return;

        if (movies.size() < 10) {
            adapter.setMovies(movies);
            rcv.setAdapter(adapter);
        } else {
            List<Movie> list = new ArrayList<>();
            for (int i=0; i<10; i++) {
                list.add(movies.get(i));
            }
            adapter.setMovies(list);
            rcv.setAdapter(adapter);
        }


    }
}