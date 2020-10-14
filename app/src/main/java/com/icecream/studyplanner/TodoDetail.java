package com.icecream.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TodoDetail extends AppCompatActivity {

    private ImageButton backbtn;
    private TextView title;
    private TextView description;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();
        backbtn = (ImageButton) findViewById(R.id.backbtn);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);


        title.setText(intent.getStringExtra("todoDetailTitle"));
        description.setText(intent.getStringExtra("todoDetailDescription"));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭
                finish();

            }
        });


    }


}