package com.example.appy.technictasks.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.example.appy.technictasks.R;

/**
 * Created by emma on 11/04/2016.
 */
public class TimerPicker extends AppCompatActivity {
    TimePicker time_picker;
    at.markushi.ui.CircleButton finish_btn;
    String time_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.time_picker);

        time_picker = (TimePicker) findViewById(R.id.timePicker);
        time_picker.setIs24HourView(true);

        // set current hour + minute on store time (test with build)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            time_str = time_picker.getHour() + ":" + time_picker.getMinute();
        } else {
            time_str = time_picker.getCurrentHour() + ":" + time_picker.getCurrentMinute();
        }

        // Time change => store time
        time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time_str = hourOfDay + ":" + minute;
            }
        });


        finish_btn = (at.markushi.ui.CircleButton) findViewById(R.id.finish);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("session", Activity.MODE_PRIVATE);
                pref.edit().remove("UserToken").commit();

                Intent intent = new Intent(TimerPicker.this, EditTask.class);
                intent.putExtra("date", getIntent().getStringExtra("date"));
                Log.v("DateTime :", time_str);
                intent.putExtra("time", time_str);
                intent.putExtra("edits", getIntent().getStringArrayListExtra("edits"));
                intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
                startActivity(intent);
            }
        });
    }
}