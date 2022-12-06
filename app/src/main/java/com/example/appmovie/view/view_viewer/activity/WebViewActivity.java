package com.example.appmovie.view.view_viewer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.appmovie.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        savedInstanceState = this.getIntent().getExtras();
        String link = (String) savedInstanceState.get("KEY_LINK");

        if (link.isEmpty()) {
            Toast.makeText(this, "Invalid link!", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);

        loadWeb(link);
    }

    private void loadWeb(String link) {
        webView.loadUrl(link);
    }
}