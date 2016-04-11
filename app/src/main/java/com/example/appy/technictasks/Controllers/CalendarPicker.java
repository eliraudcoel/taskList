package com.example.appy.technictasks.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.R;

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

        //sets the main layout of the activity
        setContentView(R.layout.calendar_picker);

        calendar = (CalendarView) findViewById(R.id.calendarPick);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                // Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                date = year +"-"+ month + "-" + day;
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