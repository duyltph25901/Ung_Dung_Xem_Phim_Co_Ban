package com.example.appmovie.view.view_ad.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adpter_view_pager_2.ContainerADAdapter;
import com.example.appmovie.class_supported.viewpager_motion_effect.ZoomOutPageTransformer;

public class HomeAdActivity extends AppCompatActivity {
    private ViewPager2 layoutContent;
    private LinearLayout layoutHome, layoutDirector, layoutActor, layoutMovie;
    private TextView txtHome, txtDirector, txtActor, txtMovie;

    private ContainerADAdapter adapter;

    private static final int TAB_HOME = 0;
    private static final int TAB_DIRECTOR = 1;
    private static final int TAB_ACTOR = 2;
    private static final int TAB_MOVIE = 3;
    private int currentIndex = TAB_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ad);

        init();
        setEvent();
    }

    private void init() {
        layoutHome = findViewById(R.id.layoutHome);
        layoutDirector = findViewById(R.id.layoutDirector);
        layoutActor = findViewById(R.id.layoutActor);
        layoutMovie = findViewById(R.id.layoutMovies);
        txtHome = findViewById(R.id.txtHome);
        txtDirector = findViewById(R.id.txtDirector);
        txtActor = findViewById(R.id.txtActor);
        txtMovie = findViewById(R.id.txtMovies);
        layoutContent = findViewById(R.id.layoutContentAd);

        adapter = new ContainerADAdapter(HomeAdActivity.this);
        layoutContent.setAdapter(adapter);
        layoutContent.setUserInputEnabled(false);
        layoutContent.setPageTransformer(new ZoomOutPageTransformer());
    }

    private void setEvent() {
        layoutHome.setOnClickListener(v -> replaceLayoutHome());
        layoutDirector.setOnClickListener(v -> replaceLayoutDirector());
        layoutActor.setOnClickListener(v -> replaceLayoutActor());
        layoutMovie.setOnClickListener(v -> replaceLayoutMovie());
    }

    private void replaceLayoutHome() {
        if (currentIndex != TAB_HOME) {
            currentIndex = TAB_HOME;

            // unselected other tab
            txtDirector.setVisibility(View.GONE);
            txtActor.setVisibility(View.GONE);
            txtMovie.setVisibility(View.GONE);

            layoutDirector.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutActor.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutMovie.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab home
            txtHome.setVisibility(View.VISIBLE);
            layoutHome.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutHome.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(0);
        }
    }

    private void replaceLayoutDirector() {
        if (currentIndex != TAB_DIRECTOR) {
            currentIndex = TAB_DIRECTOR;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtActor.setVisibility(View.GONE);
            txtMovie.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutActor.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutMovie.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab director
            txtDirector.setVisibility(View.VISIBLE);
            layoutDirector.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutDirector.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(1);
        }
    }

    private void replaceLayoutActor() {
        if (currentIndex != TAB_ACTOR) {
            currentIndex = TAB_ACTOR;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtDirector.setVisibility(View.GONE);
            txtMovie.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutDirector.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutMovie.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab actor
            txtActor.setVisibility(View.VISIBLE);
            layoutActor.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutActor.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(2);
        }
    }

    private void replaceLayoutMovie() {
        if (currentIndex != TAB_MOVIE) {
            currentIndex = TAB_MOVIE;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtActor.setVisibility(View.GONE);
            txtDirector.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutActor.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutDirector.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab movie
            txtMovie.setVisibility(View.VISIBLE);
            layoutMovie.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutMovie.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(3);
        }
    }
}