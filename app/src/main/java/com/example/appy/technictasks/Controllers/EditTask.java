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

/**
 * Created by Emmanuelle on 27/03/2016.
 */
public class EditTask extends AppCompatActivity {

    private Task task;
    TextView spendTime, comment, dateNewRdv, descriptionNewRdv;
    RelativeLayout newTask;
    CheckBox terminated, newRdv;

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
                base.close();

                Intent taskIntent = new Intent(EditTask.this, ListTasks.class);
                taskIntent.putExtra("current_user_id", getIntent().getIntExtra("current_user_id", 0));
                startActivity(taskIntent);
            }
        });
    }

    private void removeInformation() {
        dateNewRdv.setText(null);
        descriptionNewRdv.setText(null);
    }
}