package com.example.appmovie.view.view_ad.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.MovieAdapterAd;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Movie;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private ImageView imgClearDialog;
    private EditText inputLinkTrailerUpdate, inputLinkMovieUpdate, inputMovieNameUpdate, inputLimitAgeUpdate, inputPointUpdate;
    private Button btnUpdate;
    private EditText inputSearchMovieName;
    private ImageView imgSearchMovieName;
    private LottieAnimationView lottieAnimationView;

    private List<Movie> movies;
    private MovieAdapterAd adapterAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        init();
        imgSearchMovieName.setOnClickListener(v -> search());
    }

    private void init() {
        rcv = findViewById(R.id.rcvMovieAd);
        inputSearchMovieName = findViewById(R.id.inputSearchMovieAd);
        imgSearchMovieName = findViewById(R.id.imgSearchMovieAd);
        lottieAnimationView = findViewById(R.id.imageNoDataMovieAd);

        movies = new ArrayList<>();
        adapterAd = new MovieAdapterAd(new MovieAdapterAd.ClickItem() {
            @Override
            public void delete(Movie movie) {

                new AlertDialog.Builder(MovieActivity.this)
                        .setTitle("Confirm delete")
                        .setMessage("Do you want to delete "+movie.getMovieName()+"?")
                        .setNegativeButton("Yes", (dialog, which) -> {
                            MovieDatabase.getInstance(MovieActivity.this).movieDAO().delete(movie);
                            loadData();
                            Toast.makeText(MovieActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .setPositiveButton("No", null)
                        .show();
            }

            @Override
            public void update(Movie movie) {
                showDialogUpdate(movie);
            }
        });
        adapterAd.setMovies(movies);

        loadData();

        rcv.setAdapter(adapterAd);
    }

    private void loadData() {
        movies = MovieDatabase.getInstance(MovieActivity.this).movieDAO().getMovie();
        adapterAd.setMovies(movies);
    }

    private void showDialogUpdate(Movie movie) {
        final Dialog dialogUpdate = new Dialog(MovieActivity.this);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setContentView(R.layout.dialog_update_movie);
        Window window = dialogUpdate.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialogUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        init(dialogUpdate);
        
        btnUpdate.setOnClickListener(v -> update(movie, dialogUpdate));
        imgClearDialog.setOnClickListener(v -> dialogUpdate.cancel());

        dialogUpdate.show();;
    }
    
    private void init(Dialog dialog) {
        imgClearDialog = dialog.findViewById(R.id.imgClearDialogUpdateMovie);
        inputLinkTrailerUpdate = dialog.findViewById(R.id.inputLinkTrailerMovieUpdate);
        inputMovieNameUpdate = dialog.findViewById(R.id.inputMovienameUpdate);
        inputLinkMovieUpdate = dialog.findViewById(R.id.inputLinkMovieUpdate);
        inputLimitAgeUpdate = dialog.findViewById(R.id.inputLimitAgeUpdate);
        inputPointUpdate = dialog.findViewById(R.id.inputPointUpdate);
        btnUpdate = dialog.findViewById(R.id.btnUpdateMovie);
    }
    
    private void update(final Movie movie, final  Dialog dialog) {

        String linkTrailerUpdate = inputLinkTrailerUpdate.getText().toString().trim();
        String linkMovieUpdate = inputLinkMovieUpdate.getText().toString().trim();
        String movieNameUpdate = inputMovieNameUpdate.getText().toString().trim();
        String limitAgeText = inputLimitAgeUpdate.getText().toString().trim();
        String pointText = inputPointUpdate.getText().toString().trim();
        byte[] poster = movie.getPoster();
        String directors = movie.getDirectors();
        String actors = movie.getActors();
        String premiereSchedule = movie.getPremiereSchedule();
        String category = movie.getCategory();
        String summary = movie.getSummary();
        int time = movie.getTime();
        
        boolean isNullInput = isNullInput(linkMovieUpdate, linkTrailerUpdate, movieNameUpdate,limitAgeText, pointText);
        if (isNullInput) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isLinkTrailer = isUrl(linkTrailerUpdate);
        boolean isLinkMovie = isUrl(linkMovieUpdate);
        if (!isLinkTrailer) {
            Toast.makeText(this, "The trailer link is malformed!", Toast.LENGTH_SHORT).show();
            return;
        } if (!isLinkMovie) {
            Toast.makeText(this, "The movie link is not in the correct format!", Toast.LENGTH_SHORT).show();
            return;
        }

        int ageLimit = 13;
        double point = 0.0;
        try {
            ageLimit = Integer.valueOf(limitAgeText);
            point = Double.valueOf(pointText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please double check the input data!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ageLimit < 13) {
            Toast.makeText(this, "Minimum age must be greater than or equal to 13!", Toast.LENGTH_SHORT).show();
            return;
        } if (point > 10.0) {
            Toast.makeText(this, "Invalid score!", Toast.LENGTH_SHORT).show();
            return;
        }

        MovieDatabase.getInstance(MovieActivity.this).movieDAO().delete(movie);
        Movie movieNew = new Movie(
                poster,
                linkTrailerUpdate, linkMovieUpdate, movieNameUpdate,
                directors, actors, premiereSchedule, category, summary, time,
                ageLimit, point
        );
        MovieDatabase.getInstance(MovieActivity.this).movieDAO().insert(movieNew);

        loadData();
        dialog.cancel();
        Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show();
    }
    
    private boolean isNullInput(String... input) {
        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) return true;
        }
        
        return false;
    }

    private boolean isUrl(String input) {
        URL u = null;
        boolean result = false;
        try {
            u = new URL(input);
            u.toURI();

            result = true;
        } catch (MalformedURLException e) {
            result = false;
        } catch (URISyntaxException e) {
            result = false;
        }

        return result;
    }

    private void search() {
        String key = inputSearchMovieName.getText().toString().trim();
        if (key.isEmpty()) {
            loadData();
            rcv.setAdapter(adapterAd);
            rcv.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
        } else {
            movies = new ArrayList<>();
            movies = MovieDatabase.getInstance(this).movieDAO().filterMovieByName(key);

            if (movies.size() == 0 || movies.isEmpty()) {
                rcv.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
            } else {
                rcv.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                adapterAd.setMovies(movies);
                rcv.setAdapter(adapterAd);
            }
        }
    }
}