package com.icecream.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

public class AboutActivity extends AppCompatActivity {

    private ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backbtn = (ImageButton) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭
                finish();

            }
        });

        WebView wv = (WebView)this.findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadDataWithBaseURL(null, "<html><head></head><body>test</body></html>", "text/html", "utf-8", null);
    }
}