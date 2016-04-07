package com.example.appy.technictasks.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.Models.Task;
import com.example.appy.technictasks.Models.User;
import com.example.appy.technictasks.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTasks extends AppCompatActivity {

    ListView listView;
    at.markushi.ui.CircleButton log_out;
    User current_user;
    ArrayList<Task> tasks;
    HashMap<Integer, String> section_titles = new HashMap<>();
    public static DB base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        base = new DB(getBaseContext());
        base.open();
        current_user = base.getUserById(getIntent().getIntExtra("current_user_id", 1));
        base.close();

        tasks = current_user.getTasks(getBaseContext());
        setSectionTitles();

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ListAdapterTask(this, tasks, section_titles));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListAdapterTask adapter = (ListAdapterTask) parent.getAdapter();
                Task task = (Task) adapter.getItem(position);

                Intent intent = new Intent(ListTasks.this, TaskDetail.class);
                intent.putExtra("current_user_id", current_user.getId());
                intent.putExtra("task_id", task.getId());
                startActivity(intent);
            }
        });

        log_out = (at.markushi.ui.CircleButton) findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getPreferences(Activity.MODE_PRIVATE);
                pref.edit().remove("Usertoken").commit();

                current_user.setToken(null);
                base = new DB(getBaseContext());
                base.open();
                base.updateUser(current_user.getId(), current_user);
                base.close();

                goToMain();
            }
        });
    }

    protected void setSectionTitles() {
        for (Task task:tasks) {
            String date_str = task.getDate().split(" ")[0];

            if(section_titles == null || !section_titles.containsValue(date_str)) {
                section_titles.put(tasks.indexOf(task), date_str);
            }
        }
    }

    private void goToMain() {
        Intent intent = new Intent(ListTasks.this, MainActivity.class);
        startActivity(intent);
    }
}
