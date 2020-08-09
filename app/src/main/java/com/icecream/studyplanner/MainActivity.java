package com.icecream.studyplanner;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

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
    private JSONArray jsonarrayTODO = new JSONArray();

    private JSONArray jsonArray2;
    private ArrayList<String>titleList;
    private ArrayList<String>DescriptionList;
    private ArrayList<String>dateList;
    private String previousTodo;
    public static String roadJSONTodo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        titleList = new ArrayList<>();
        DescriptionList = new ArrayList<>();
        dateList = new ArrayList<>();


/** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .build();

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
        String i = sharedpreferences.getString("todaytotalTime", "0");
        String savedate = sharedpreferences.getString("savedate", formattedDate);

        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Log.e("ms", i);
        String displayedTimer = formatter.format(new Date(Integer.parseInt(i)));
        todayTotalTime.setText(displayedTimer);


        roadJSONTodo = sharedpreferences.getString("todoJson", ""); //기존 투두 데이터

        previousTodo = roadJSONTodo;
        try {
            JSONArray jsonarray = new JSONArray(roadJSONTodo);
            jsonarrayTODO = new JSONArray(previousTodo);
            for(int k=0; k < jsonarray.length(); k++) {
                JSONObject jsonobject = jsonarray.getJSONObject(k);
                String name       = jsonobject.getString("name");
                String content    = jsonobject.getString("content");
                String date  = jsonobject.getString("date");

                Todo todo = new Todo(name, "");
                mArrayList.add(0, todo); //RecyclerView의 첫 줄에 삽입
            }

        } catch (JSONException e) {
            Log.w("error", "something error!!", e);
        }







        if(!savedate.equals(formattedDate)) { //하루 지나면 이전 데이터 삭제
            sharedpreferences.edit().remove("todaytotalTime").commit();
            todayTotalTime.setText("00:00:00");

        } else {
            todayTotalTime.setText(displayedTimer); //오늘하루 누적 시간 로딩
        }


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                DateFormat formatter = new SimpleDateFormat("YYYY-M-d", Locale.KOREA);
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Toast.makeText(MainActivity.this, formatter.format(date), Toast.LENGTH_SHORT).show();
            }
        });



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
        if(data == null) {
            //데이터 전달 없이 액티비티 닫힘

        } else {
            if (requestCode == 1) { //타이머 액티비티
                if(resultCode == RESULT_OK) {
                    String addableTime = data.getStringExtra("time");
                    DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String i = sharedpreferences.getString("todaytotalTime", "0"); //기존 데이터

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = df.format(c);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("todaytotalTime", String.valueOf(Integer.parseInt(i) + Integer.parseInt(addableTime))); //ms초 저장
                    editor.putString("savedate", formattedDate); //오늘날짜
                    editor.apply();

                    String finalData = sharedpreferences.getString("todaytotalTime", "0"); //기존 데이터

                    String displayedTimer = formatter.format(new Date(Integer.parseInt(finalData)));


                    todayTotalTime.setText(displayedTimer);




                }
            } else if(requestCode==2) { //todoedit 액티비티

                final JSONObject object = new JSONObject();

                String name = data.getStringExtra("todotitle");
                String content = data.getStringExtra("content");
                String date = data.getStringExtra("date");
                //JSON데이터 생성

                try {
                    object.put("name", name);
                    object.put("content", content);
                    object.put("date", date);


                    jsonarrayTODO.put(object);
                    Log.e("data", jsonarrayTODO.toString());
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("todoJson", jsonarrayTODO.toString()); //투두 => json으로 저장
                    editor.apply();

                    try {
                        jsonArray2 = new JSONArray(jsonarrayTODO.toString());
                        titleList.clear();
                        DescriptionList.clear();
                        dateList.clear();
                        for(int i = 0 ; i<jsonarrayTODO.length(); i++){
                            JSONObject jsonObject = jsonarrayTODO.getJSONObject(i);
                            String title = jsonObject.getString("name");
                            String description = jsonObject.getString("content");
                            String date_ = jsonObject.getString("date");
                            titleList.add(title);
                            DescriptionList.add(description);
                            dateList.add(date_);
                        }

                        Log.e("debug",titleList.toString());

                        Todo todo = new Todo(name, "");
                        mArrayList.add(0, todo); //RecyclerView의 첫 줄에 삽입

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }





                mRecyclerView.smoothScrollToPosition(0);
                mAdapter.notifyDataSetChanged();
            }

        }

    }



}