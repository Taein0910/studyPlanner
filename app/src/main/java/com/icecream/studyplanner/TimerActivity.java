package com.icecream.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    TextView timer ;
    Button start, pause, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Hours, Seconds, Minutes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_acitivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        timer = (TextView)findViewById(R.id.tvTimer);
        pause = (Button)findViewById(R.id.btPause);

        handler = new Handler() ;

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);



        //종료
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                Log.e("debug", String.valueOf(MillisecondTime));

                Intent intent = new Intent();
                intent.putExtra("time", String.valueOf(MillisecondTime));
                setResult(RESULT_OK, intent);
                finish();



            }
        });


    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;



            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            Hours = (int) Minutes / 60;


            timer.setText("" + String.format("%02d", Hours) + ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };
}