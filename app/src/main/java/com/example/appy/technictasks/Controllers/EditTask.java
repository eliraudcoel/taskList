package com.example.appy.technictasks.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.Models.Task;
import com.example.appy.technictasks.R;

import java.util.ArrayList;

/**
 * Created by Emmanuelle on 27/03/2016.
 */
public class EditTask extends AppCompatActivity {

    private Task task;
    TextView spendTime, comment, dateNewRdv, descriptionNewRdv;
    RelativeLayout newTask;
    CheckBox terminated, newRdv;
    ArrayList<String> editStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        DB base = new DB(getBaseContext());
        base.open();
        task = base.getTaskById(getIntent().getIntExtra("task_id", 0));
        base.close();

        spendTime = (TextView) findViewById(R.id.spend_time);
        comment = (TextView) findViewById(R.id.comment);
        terminated = (CheckBox) findViewById(R.id.terminated);

        dateNewRdv = (TextView) findViewById(R.id.date_new_rdv);
        descriptionNewRdv = (TextView) findViewById(R.id.description_new_rdv);

        // Display newTask form only if selected
        newTask = (RelativeLayout) findViewById(R.id.NewTask);
        newRdv = (CheckBox) findViewById(R.id.new_rdv);
        newRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newRdv.isChecked()) {
                    newTask.setVisibility(View.VISIBLE);
                } else {
                    removeInformation();
                    newTask.setVisibility(View.GONE);
                }
            }
        });

        dateNewRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent(EditTask.this, CalendarPicker.class);
                setStrings();
                calendarIntent.putExtra("edits", editStrings);
                calendarIntent.putExtra("task_id", task.getId());
                startActivity(calendarIntent);
            }
        });


        // button
        Button validButton = (Button) findViewById(R.id.valid_btn);
        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTime_spend(spendTime.getText().toString());
                if(terminated.isChecked() == true) { task.setTerminated("true"); }
                if(terminated.isChecked() == false) { task.setTerminated("false"); }
                task.setComment(comment.getText().toString());

                DB base = new DB(getBaseContext());
                base.open();

                base.updateTask(task.getId(), task);

                if(newRdv.isChecked()) {
                    base.insertTask(createNewTask());
                }

                base.close();

                Intent taskIntent = new Intent(EditTask.this, ListTasks.class);
                taskIntent.putExtra("current_user_id", getIntent().getIntExtra("current_user_id", 0));
                startActivity(taskIntent);
            }
        });

        /*
        ArrayList<String> edits_data = getIntent().getStringArrayListExtra("edits");
        if (edits_data != null) {
            fillIn(edits_data);
        }
        */

        String new_date = getIntent().getStringExtra("date");
        String new_time = getIntent().getStringExtra("time");

        if (new_date != null && new_time != null) {
            dateNewRdv.setText(new_date + " "+ new_time);
        }
    }

    private void fillIn(ArrayList<String> data) {
        spendTime.setText(data.get(0));
        comment.setText(data.get(1));
        terminated.setChecked(Boolean.parseBoolean(data.get(2)));
        newRdv.setChecked(Boolean.parseBoolean(data.get(2)));
        descriptionNewRdv.setText(data.get(3));
    }

    private void setStrings() {
        editStrings = new ArrayList<>();
        editStrings.add(spendTime.getText().toString());
        editStrings.add(comment.getText().toString());
        editStrings.add(""+terminated.isChecked());
        editStrings.add(""+newRdv.isChecked());
        editStrings.add(descriptionNewRdv.getText().toString());
    }

    private void removeInformation() {
        dateNewRdv.setText(null);
        descriptionNewRdv.setText(null);
    }

    private Task createNewTask() {
        return new Task(task.getCustomer(), dateNewRdv.getText().toString(), task.getMachine_type(), task.getMachine_brand(), descriptionNewRdv.getText().toString(), task.getUser());
    }
}