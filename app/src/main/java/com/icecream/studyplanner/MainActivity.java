package com.icecream.studyplanner;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends Activity {

    private ArrayList<Todo> mArrayList;
    private CustomAdapter mAdapter;
    private int count = -1;

    private TextView todayTotalTime;
    private Button timerButton;
    private Button addButton;
    private RecyclerView mRecyclerView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        todayTotalTime = (TextView) findViewById(R.id.todayTotalTime);
        timerButton = (Button) findViewById(R.id.timerButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        addButton = (Button) findViewById(R.id.addbtn);

        /////
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new CustomAdapter( mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        //////

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String i = sharedpreferences.getString("todaytotalTime", "00:00:00");
        String savedate = sharedpreferences.getString("savedate", formattedDate);

        if(!savedate.equals(formattedDate)) { //하루 지나면 이전 데이터 삭제
            sharedpreferences.edit().remove("todaytotalTime").commit();
            todayTotalTime.setText("00:00:00");

        } else {
            todayTotalTime.setText(i);
        }



        //타이머 시작
        timerButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TimerActivity.class);
                startActivityForResult(i, 1);
            }
        });
        //

        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditTodo.class);
                startActivityForResult(i, 2);
            }
        });
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String addableTime = data.getStringExtra("time");
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String displayedTimer = formatter.format(new Date(Integer.parseInt(addableTime)));

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String i = sharedpreferences.getString("todaytotalTime", "00:00:00");

                todayTotalTime.setText(displayedTimer);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = df.format(c);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("todaytotalTime", displayedTimer);
                editor.putString("savedate", formattedDate);
                editor.apply();
            }
        } else if(requestCode==2) {
            String name = data.getStringExtra("todotitle");
            Todo todo = new Todo(name, "");

            mArrayList.add(0, todo); //RecyclerView의 첫 줄에 삽입

            mRecyclerView.smoothScrollToPosition(0);
            mAdapter.notifyDataSetChanged();
        }
    }

}