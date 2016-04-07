package com.example.appy.technictasks.Controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.Controllers.ChronometerService.MyBinder;
import com.example.appy.technictasks.Models.Task;
import com.example.appy.technictasks.R;

import java.util.Timer;
import java.util.TimerTask;

public class ChronometerTask extends AppCompatActivity {

    private Task task;
    boolean mServiceBound = false;
    TextView timeTextView;
    Timer myTimer;
    ChronometerService chronometerService;
    long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer_task);

        DB base = new DB(getBaseContext());
        base.open();
        task = base.getTaskById(getIntent().getIntExtra("task_id", 0));
        base.close();

        timeTextView = (TextView) findViewById(R.id.time_textview);
        ImageButton startButton = (ImageButton) findViewById(R.id.start_btn);
        ImageButton pauseButton = (ImageButton) findViewById(R.id.pause_btn);
        Button endButton = (Button) findViewById(R.id.stop_btn);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTimer = new Timer();
                mServiceBound = true;
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (mServiceBound) {
                            if(timeWhenStopped != 0) { chronometerService.setChronoBase(SystemClock.elapsedRealtime() + timeWhenStopped); }
                            timeChange();
                        }
                    }

                }, 0, 1000);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                    timeWhenStopped = chronometerService.getChronoBase() - SystemClock.elapsedRealtime();
                    myTimer.cancel();
                }
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent taskIntent = new Intent(ChronometerTask.this, TaskDetail.class);
                taskIntent.putExtra("task", task);
                startActivity(taskIntent);
                */

                Intent taskIntent = new Intent(ChronometerTask.this, EditTask.class);
                taskIntent.putExtra("task_id", task.getId());
                taskIntent.putExtra("current_user_id", getIntent().getIntExtra("current_user_id", 0));
                startActivity(taskIntent);
            }
        });
    }

    protected void timeChange() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            timeTextView.setText(chronometerService.getTimestamp());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ChronometerService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder myBinder = (MyBinder) service;
            chronometerService = myBinder.getService();
            mServiceBound = true;
        }
    };
}
