package com.example.careforadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        String url = getIntent().getStringExtra("ExpURL");
        WebView webView = findViewById(R.id.web2);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}