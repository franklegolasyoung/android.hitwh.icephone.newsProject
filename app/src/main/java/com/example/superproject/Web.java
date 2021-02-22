package com.example.superproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Web extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        back = findViewById(R.id.btn_back);
        webView = findViewById(R.id.wView);
        back.setOnClickListener(this);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        webView.loadUrl(uri);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) finish();
    }
}
