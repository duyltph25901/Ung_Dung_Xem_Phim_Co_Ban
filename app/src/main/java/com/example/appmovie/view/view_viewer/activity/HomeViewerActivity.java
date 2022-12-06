package com.example.appmovie.view.view_viewer.activity;

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
import com.example.appmovie.adapter.adpter_view_pager_2.ContainerViewerAdapter;
import com.example.appmovie.class_supported.viewpager_motion_effect.DepthPageTransformer;

public class HomeViewerActivity extends AppCompatActivity {
    private ViewPager2 layoutContent;
    private LinearLayout layoutHome, layoutFavorite, layoutUser;
    private TextView txtHome, txtFavorite, txtUser;

    private ContainerViewerAdapter adapter;

    private static final int TAB_HOME = 0;
    private static final int TAB_FAVORITE = 1;
    private static final int TAB_USER = 2;
    private int currentIndex = TAB_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_viewer);

        init();
        setEvent();
    }

    private void init() {
        layoutContent = findViewById(R.id.layoutContentViewer);
        layoutHome = findViewById(R.id.layoutHomeViewer);
        layoutFavorite = findViewById(R.id.layoutFavoriteViewer);
        layoutUser = findViewById(R.id.layoutUserViewer);
        txtHome = findViewById(R.id.txtHomeViewer);
        txtFavorite = findViewById(R.id.txtFavoriteViewer);
        txtUser = findViewById(R.id.txtUserViewer);

        adapter = new ContainerViewerAdapter(HomeViewerActivity.this);
        layoutContent.setAdapter(adapter);
        layoutContent.setUserInputEnabled(false);
        layoutContent.setPageTransformer(new DepthPageTransformer());
    }

    private void setEvent() {
        layoutHome.setOnClickListener(v -> replaceTabHome());
        layoutFavorite.setOnClickListener(v -> replaceTabFavorite());
        layoutUser.setOnClickListener(v -> replaceTabUser());
    }

    private void replaceTabHome() {
        if (currentIndex != TAB_HOME) {
            currentIndex = TAB_HOME;

            // unselected other tab
            txtFavorite.setVisibility(View.GONE);
            txtUser.setVisibility(View.GONE);

            layoutFavorite.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutUser.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab home
            txtHome.setVisibility(View.VISIBLE);
            layoutHome.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutHome.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(currentIndex);
        }
    }

    private void replaceTabFavorite() {
        if (currentIndex != TAB_FAVORITE) {
            currentIndex = TAB_FAVORITE;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtUser.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutUser.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab favorite
            txtFavorite.setVisibility(View.VISIBLE);
            layoutFavorite.setBackgroundResource(R.drawable.rounded_bottom_bar);

            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutFavorite.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(currentIndex);
        }
    }

    private void replaceTabUser() {
        if (currentIndex != TAB_USER) {
            currentIndex = TAB_USER;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtFavorite.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutFavorite.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab user
            txtUser.setVisibility(View.VISIBLE);
            layoutUser.setBackgroundResource(R.drawable.rounded_bottom_bar);

            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutUser.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(currentIndex);
        }
    }
}