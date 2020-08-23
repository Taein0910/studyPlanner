package com.icecream.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditTodo extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener callbackMethod;

    private TextView textView;
    private TextView textView2;

    private Button button;
    private Button datapickerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textView = (TextView) findViewById(R.id.titleEdt);
        textView2 = (TextView) findViewById(R.id.description);

        button = (Button) findViewById(R.id.savebtn);
        datapickerBtn = (Button) findViewById(R.id.datapickerbtn);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(textView.getText()) || TextUtils.isEmpty(textView2.getText()) || datapickerBtn.getText().equals("날짜선택")) {
                    Toast.makeText(EditTodo.this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("todotitle", textView.getText().toString());
                    intent.putExtra("content", textView2.getText().toString());
                    intent.putExtra("date", datapickerBtn.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        datapickerBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog =
                        new DatePickerDialog(EditTodo.this, callbackMethod, mYear, mMonth, mDay);
                dialog.show();

            }
        });


        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                    datapickerBtn.setText((year+"-"+(monthOfYear+1)+ "-" + dayOfMonth));

            }
        };



    }

    //EditText 아닌 곳 누르면 키보드숨기기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


}