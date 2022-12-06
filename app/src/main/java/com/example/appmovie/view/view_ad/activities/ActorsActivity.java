package com.example.appmovie.view.view_ad.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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

import com.example.appmovie.R;
import com.example.appmovie.adapter.adapter_rcv.ActorAdapterAd;
import com.example.appmovie.db.db_actor.ActorDatabase;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Actor;
import com.example.appmovie.model.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActorsActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private EditText inputFullNameActorUpdate;
    private TextView txtDateOfBirthActorUpdate;
    private Spinner spnCountriesActorUpdate;
    private Button btnDone;
    private ImageView imgClearDialogUpdate;
    private EditText inputSearchActorName;
    private ImageView imgSearchActorName;

    private ActorAdapterAd adapterAd;
    private List<Actor> actors;
    private List<Movie> movies;
    private List<String> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors);

        init();

        imgSearchActorName.setOnClickListener(v -> searchActorName());
    }

    private void init() {
        movies = new ArrayList<>();
        movies = MovieDatabase.getInstance(ActorsActivity.this).movieDAO().getMovie();

        rcv = findViewById(R.id.rcvActorAd);
        inputSearchActorName = findViewById(R.id.inputSearchActor);
        imgSearchActorName = findViewById(R.id.imgSearchActor);
        adapterAd = new ActorAdapterAd(new ActorAdapterAd.ClickItem() {
            @Override
            public void update(Actor actor) {
                showDialogUpdate(actor);
            }

            @Override
            public void delete(Actor actor) {
                new AlertDialog.Builder(ActorsActivity.this)
                        .setTitle("Confirm delete")
                        .setMessage("Do you want to delete "+actor.getFullName()+"?")
                        .setNegativeButton("Yes", (dialog, which) -> {
                            boolean flag = false;
                            String actorName = actor.getFullName();
                            if (movies.size() == 0) return;
                            for (int i=0; i<movies.size(); i++) {
                                if (movies.get(i).getActors().indexOf(actorName) >= 0) {
                                    flag = true;
                                }
                            }

                            if (flag) {
                                Toast.makeText(ActorsActivity.this, "You cannot perform this operation!!", Toast.LENGTH_SHORT).show();

                                return;
                            }

                            ActorDatabase.getInstance(ActorsActivity.this)
                                    .actorDAO().delete(actor);
                            loadData();
                            Toast.makeText(ActorsActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .setPositiveButton("No", null)
                        .show();
            }
        });
        adapterAd.setActors(actors);

        loadData();

        rcv.setAdapter(adapterAd);
    }

    private void loadData() {
        actors = ActorDatabase.getInstance(ActorsActivity.this).actorDAO().getActor();
        adapterAd.setActors(actors);
    }

    private void showDialogUpdate(Actor actor) {
        final Dialog dialogUpdate = new Dialog(ActorsActivity.this);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setContentView(R.layout.dialog_update_actor_director_ad);
        Window window = dialogUpdate.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialogUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init(dialogUpdate);
        setDataForSpn();

        imgClearDialogUpdate.setOnClickListener(v -> dialogUpdate.cancel());
        btnDone.setOnClickListener(v -> update(actor, dialogUpdate));
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
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(ActorsActivity.this, android.R.layout.simple_spinner_dropdown_item,
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

    private void update(Actor actor, Dialog dialog) {
        String fullNameActorUpdate = inputFullNameActorUpdate.getText().toString().trim();
        String dobUpdate = txtDateOfBirthActorUpdate.getText().toString().trim();
        String countriesUpdate = spnCountriesActorUpdate.getSelectedItem().toString().trim();
        byte[] bitAvt = actor.getImg();
        String gender = actor.getGender();
        String story = actor.getStory();

        boolean isNullInput = isNullInput(fullNameActorUpdate, dobUpdate);
        if (isNullInput) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update actor in table movie
        String actorOldName = actor.getFullName().toString();
        List<Movie> movies = MovieDatabase.getInstance(ActorsActivity.this)
                .movieDAO().searchMovieByActorName(actorOldName);
        int size = movies.size();
        if (size == 0) return;
        for (int i=0; i<size; i++) {
            Movie movie = movies.get(i);
            if(movie == null) return;
            String[] arr = movie.getActors().toString().split(",");
            String actors = "";
            // Clear space in head and last
            for (int j=0; j<arr.length; j++) {
                if ( arr[j].isEmpty() || arr[j].length()==0) arr[j] = "";
                arr[j] = arr[j].trim();
            }

            // Replace old name
            for (int j=0; j<arr.length; j++) {
                if (arr[j].toString().matches(actorOldName)) {
                    arr[j] = fullNameActorUpdate;
                }
            }

            // step end
            for (int j=0; j<arr.length; j++) {
                if (arr[j].isEmpty() || arr[j].length()==0) {
                    ++j;
                    continue;
                }
                actors += arr[j] + ", ";
            }

            // Get data old from movie
            String linkTrailer = movie.getLinkTrailer();
            String linkMovie = movie.getLinkFilm();
            String movieName = movie.getMovieName();
            byte[] poster = movie.getPoster();
            String directors = movie.getDirectors();
            String premiereSchedule = movie.getPremiereSchedule();
            String category = movie.getCategory();
            String summary = movie.getSummary();
            int limitAge = movie.getLimitAge();
            int time = movie.getTime();
            double point = movie.getPoint();

            MovieDatabase.getInstance(ActorsActivity.this)
                    .movieDAO().delete(movie);
            Movie movieNew = new Movie(
                    poster, linkTrailer, linkMovie,
                    movieName, directors, actors, premiereSchedule, category, summary, time, limitAge, point
            );
            MovieDatabase.getInstance(ActorsActivity.this).movieDAO().insert(movieNew);
        }

        // Update actor in table Actor
        Actor actorNew = new Actor(
                bitAvt, fullNameActorUpdate, dobUpdate, countriesUpdate, gender, story
        );
        ActorDatabase.getInstance(ActorsActivity.this).actorDAO().delete(actor);
        ActorDatabase.getInstance(ActorsActivity.this).actorDAO().insert(actorNew);
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

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ActorsActivity.this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtDateOfBirthActorUpdate.setText(format.format(calendar.getTime()));
        }, nam, thang ,ngay);
        datePickerDialog.show();
    }

    private void searchActorName() {
        String actorNameInput = inputSearchActorName.getText().toString().trim();
        if (actorNameInput.isEmpty()) {
            actors = new ArrayList<>();
            actors = ActorDatabase.getInstance(this).actorDAO().getActor();
            adapterAd.setActors(actors);
        } else {
            actors = new ArrayList<>();
            actors = ActorDatabase.getInstance(this).actorDAO().searchActorByName2(actorNameInput);
            adapterAd.setActors(actors);
        }
    }
}