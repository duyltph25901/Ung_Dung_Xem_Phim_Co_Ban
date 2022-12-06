package com.example.appmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.appmovie.boarding.BoardingActivity;
import com.example.appmovie.class_supported.DataLocalManagement;
import com.example.appmovie.login_signup.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( !DataLocalManagement.getFirstInstall() ) {
            // Tao man hinh cho trong vong 7s
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getBaseContext(), BoardingActivity.class));
                DataLocalManagement.setFirstInstall(true);
            }, 7000);
        } else {
//            Toast.makeText(this, "This is not the first install app!", Toast.LENGTH_SHORT).show();
            // Tao man hinh cho trong vong 7s
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
//                DataLocalManagement.setFirstInstall(false);
            }, 7000);

        }
    }
}