package com.example.appmovie.view.view_ad.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.DirectorAdapterAd;
import com.example.appmovie.db.db_director.DirectorDatabase;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Director;
import com.example.appmovie.model.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DirectorsActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private EditText inputFullNameActorUpdate;
    private TextView txtDateOfBirthActorUpdate;
    private Spinner spnCountriesActorUpdate;
    private Button btnDone;
    private ImageView imgClearDialogUpdate;
    private EditText inputSearchDirector;
    private ImageView imgSearchDirector;
    private LottieAnimationView lottieAnimationView;

    private DirectorAdapterAd adapterAd;
    private List<Director> directors;
    private List<Movie> movies;
    private List<String> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directors);

        init();
        imgSearchDirector.setOnClickListener(v -> searchDirector());
    }

    private void init() {
        movies = new ArrayList<>();
        movies = MovieDatabase.getInstance(DirectorsActivity.this).movieDAO().getMovie();

        rcv = findViewById(R.id.rcvDirectorAd);
        lottieAnimationView = findViewById(R.id.imageNoDataDirector);
        inputSearchDirector = findViewById(R.id.inputSearchDirector);
        imgSearchDirector = findViewById(R.id.imgSearchDirector);
        directors = new ArrayList<>();
        adapterAd = new DirectorAdapterAd(new DirectorAdapterAd.ClickItem() {
            @Override
            public void update(Director director) {
                showDialogUpdate(director);
            }

            @Override
            public void delete(Director director) {
                new AlertDialog.Builder(DirectorsActivity.this)
                        .setTitle("Confirm delete")
                        .setMessage("Do you want to delete "+director.getFullName()+"?")
                        .setNegativeButton("Yes", (dialog, which) -> {
                            boolean flag = false;
                            String directorName = director.getFullName();
                            if (movies.size() == 0) return;
                            for (int i=0; i<movies.size(); i++) {
                                if (movies.get(i).getDirectors().indexOf(directorName) >= 0) {
                                    flag = true;
                                }
                            }

                            if (flag) {
                                Toast.makeText(DirectorsActivity.this, "You cannot perform this operation!!", Toast.LENGTH_SHORT).show();

                                return;
                            }

                                DirectorDatabase.getInstance(DirectorsActivity.this)
                                        .directorDAO().delete(director);
                                loadData();
                                Toast.makeText(DirectorsActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .setPositiveButton("No", null)
                        .show();
            }
        });
        adapterAd.setDirectors(directors);

        loadData();

        rcv.setAdapter(adapterAd);
    }

    private void loadData() {
        directors = DirectorDatabase.getInstance(DirectorsActivity.this).directorDAO().getDirector();
        adapterAd.setDirectors(directors);
    }

    private void showDialogUpdate(Director director) {
        final Dialog dialogUpdate = new Dialog(DirectorsActivity.this);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setContentView(R.layout.dialog_update_actor_director_ad);
        Window window = dialogUpdate.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialogUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init(dialogUpdate);
        setDataForSpn();

        imgClearDialogUpdate.setOnClickListener(v -> dialogUpdate.cancel());
        btnDone.setOnClickListener(v -> update(director, dialogUpdate));
        txtDateOfBirthActorUpdate.setOnClickListener(v -> showDatePickerDialog());

        dialogUpdate.show();
    }

    private void init(Dialog dialog) {
        inputFullNameActorUpdate = dialog.findViewById(R.id.inputFullNameActorDirectorUpdate);
        txtDateOfBirthActorUpdate = dialog.findViewById(R.id.txtDOBActorDirectorUpdate);
        spnCountriesActorUpdate = dialog.findViewById(R.id.spnCountriesActorDirectorUpdate);
        btnDone = dialog.findViewById(R.id.btnDoneUpdateActorDirector);
        imgClearDialogUpdate = dialog.findViewById(R.id.imgClearDialogUpdateActor);

        countries = new ArrayList<>();
    }

    private void setDataForSpn() {
        countries = getCountries();
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(DirectorsActivity.this, android.R.layout.simple_spinner_dropdown_item,
                countries);
        spnCountriesActorUpdate.setAdapter(adapterSpn);
    }

    private List<String> getCountries() {
        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {

            Locale obj = new Locale("", countryCode);

            countries.add(obj.getDisplayCountry().trim());

        }

        return countries;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DirectorsActivity.this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtDateOfBirthActorUpdate.setText(format.format(calendar.getTime()));
        }, nam, thang ,ngay);
        datePickerDialog.show();
    }

    private void update(Director director, Dialog dialog) {
        String fullNameUpdate = inputFullNameActorUpdate.getText().toString().trim();
        String dobUpdate = txtDateOfBirthActorUpdate.getText().toString().trim();
        String countriesUpdate = spnCountriesActorUpdate.getSelectedItem().toString().trim();
        byte[] bitAvt = director.getImage();
        String gender = director.getGender();
        String story = director.getStory();
        
        boolean isNullInput = isNullInput(fullNameUpdate, dobUpdate);
        if (isNullInput) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update director in table
        String directorOldName = director.getFullName().toString().trim();
        List<Movie> movies = MovieDatabase.getInstance(DirectorsActivity.this)
                .movieDAO().searchMovieByDirectorName(directorOldName);
        int size = movies.size();
        if (size == 0) return;
        for (int i=0; i<size; i++) {
            Movie movie = movies.get(i);
            if (movie == null) return;
            String[] arr = movie.getDirectors().toString().split(",");
            String directors = "";
            // Clear space in head and last
            for (int j=0; j<arr.length; j++) {
                if ( arr[j].isEmpty() || arr[j].length()==0) arr[j] = "";
                arr[j] = arr[j].trim();
            }
            // Replace old name
            for (int j=0; j<arr.length; j++) {
                if (arr[j].toString().matches(directorOldName)) {
                    arr[j] = fullNameUpdate;
                }
            }
            // step end
            for (int j=0; j<arr.length; j++) {
                if (arr[j].isEmpty() || arr[j].length()==0) {
                    ++j;
                    continue;
                }
                directors += arr[j] + ", ";
            }
            // Get data old from movie
            String linkTrailer = movie.getLinkTrailer();
            String linkMovie = movie.getLinkFilm();
            String movieName = movie.getMovieName();
            byte[] poster = movie.getPoster();
            String actors = movie.getActors();
            String premiereSchedule = movie.getPremiereSchedule();
            String category = movie.getCategory();
            String summary = movie.getSummary();
            int limitAge = movie.getLimitAge();
            int time = movie.getTime();
            double point = movie.getPoint();

            MovieDatabase.getInstance(DirectorsActivity.this)
                    .movieDAO().delete(movie);
            Movie movieNew = new Movie(
                    poster, linkTrailer, linkMovie,
                    movieName, directors, actors, premiereSchedule, category, summary, time, limitAge, point
            );
            MovieDatabase.getInstance(DirectorsActivity.this).movieDAO().insert(movieNew);
        }

        // Update director in table director
        Director directorNew = new Director(
                bitAvt, fullNameUpdate, dobUpdate, countriesUpdate, gender, story
        );
        DirectorDatabase.getInstance(DirectorsActivity.this).directorDAO().delete(director);
        DirectorDatabase.getInstance(DirectorsActivity.this).directorDAO().insert(directorNew);
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

    private void searchDirector() {
        String directorNameInput = inputSearchDirector.getText().toString().trim();
        if (directorNameInput.isEmpty()) {
            directors = new ArrayList<>();
            directors = DirectorDatabase.getInstance(this).directorDAO().getDirector();
            adapterAd.setDirectors(directors);
            rcv.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
            rcv.setAdapter(adapterAd);
        } else {
            directors = new ArrayList<>();
            directors = DirectorDatabase.getInstance(this).directorDAO().searchDirectorByName2(directorNameInput);
            adapterAd.setDirectors(directors);

            if (directors.isEmpty() || directors.size() == 0) {
                rcv.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
            } else {
                rcv.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                rcv.setAdapter(adapterAd);
            }
        }
    }
}