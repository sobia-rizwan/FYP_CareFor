package com.example.careforadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web1);
        WebView webView = findViewById(R.id.web1);
        //Uri url = Uri.parse(getIntent().getStringExtra("Cnic"));
        String url = getIntent().getStringExtra("Cnic");
        System.out.println("URLLLLLL ISSSSSSSSSSSS: "+url);


        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}