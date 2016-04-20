package com.example.appy.technictasks.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;

import com.example.appy.technictasks.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by emma on 11/04/2016.
 */
public class CalendarPicker extends AppCompatActivity {
    CalendarView calendar;
    at.markushi.ui.CircleButton hour_btn;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_picker);

        calendar = (CalendarView) findViewById(R.id.calendarPick);
        calendar.setMinDate(new Date().getTime()); // set min date of Today

        // set by default current date
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        date = date_format.format(new Date());

        // On change => store date
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                date = "" + year;
                int real_month = month + 1;

                // Fix when < 10 => no match with 0
                if (real_month < 10) {
                    date += "-0" + real_month;
                } else {
                    date += "-" + real_month;
                }

                if (day < 10) {
                    date += "-0" + day;
                } else {
                    date += "-" + day;
                }
            }
        });


        hour_btn = (at.markushi.ui.CircleButton) findViewById(R.id.go_hour);
        hour_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("session", Activity.MODE_PRIVATE);
                pref.edit().remove("UserToken").commit();

                Intent intent = new Intent(CalendarPicker.this, TimerPicker.class);
                intent.putExtra("date", date);
                intent.putExtra("edits", getIntent().getStringArrayListExtra("edits"));
                intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
                startActivity(intent);
            }
        });
    }
}