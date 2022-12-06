package com.example.appmovie.boarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.adpter_view_pager_2.BoardingAdapter;
import com.example.appmovie.login_signup.LoginActivity;

import me.relex.circleindicator.CircleIndicator3;

public class BoardingActivity extends AppCompatActivity {
    private TextView txtSkip;
    private ViewPager2 layoutBoarding;
    private CircleIndicator3 circleIndicator3;
    private ImageButton btnNext;
    private Button btnGetStarted;

    private BoardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);

        init();
        active();
        setEvent();
    }

    private void init() {
        txtSkip = findViewById(R.id.txtSkipBoarding);
        layoutBoarding = findViewById(R.id.layoutBoarding);
        circleIndicator3 = findViewById(R.id.circleBoarding);
        btnNext = findViewById(R.id.btnNextBoarding);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        adapter = new BoardingAdapter(this);
    }

    private void active() {
        layoutBoarding.setAdapter(adapter);
        circleIndicator3.setViewPager(layoutBoarding);
    }

    private void setEvent() {
        txtSkip.setOnClickListener(v -> skip());
        btnNext.setOnClickListener(v -> next());
        btnGetStarted.setOnClickListener(v -> getStarted());
    }

    private void skip() {
        layoutBoarding.setCurrentItem(2);
    }

    private void next() {
        int currentIndex = layoutBoarding.getCurrentItem();
        ++currentIndex;
        if (currentIndex <=2) {
            layoutBoarding.setCurrentItem(currentIndex);
        } else {
            currentIndex = 0;
            layoutBoarding.setCurrentItem(currentIndex);
        }
    }

    private void getStarted() {
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
    }
}