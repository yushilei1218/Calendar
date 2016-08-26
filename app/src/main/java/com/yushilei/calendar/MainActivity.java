package com.yushilei.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText yearET;
    private EditText monthET;
    private EditText dayET;
    private MyCalendarView calendarV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yearET = (EditText) findViewById(R.id.year);
        monthET = (EditText) findViewById(R.id.month);
        dayET = (EditText) findViewById(R.id.day);
        calendarV = (MyCalendarView) findViewById(R.id.calendar);

    }

    public void showCalendar(View view) {
        String year = yearET.getText().toString();
        String month = monthET.getText().toString();
        String day = dayET.getText().toString();

        calendarV.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public void jump(View view) {
        startActivity(new Intent(this, TextBaseLineActivity.class));
    }
}
