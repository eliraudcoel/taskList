package com.example.appy.technictasks.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appy.technictasks.Models.Task;
import com.example.appy.technictasks.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Emmanuelle on 28/01/2016.
 */
public class ListAdapterTask extends BaseAdapter {

    private final Context context;

    ArrayList<Task> tasks = null;
    HashMap<Integer, String> section_titles = null;
    Integer has_already_passed_title = 0;

    public ListAdapterTask(Context context, ArrayList<Task> tasks, HashMap<Integer, String> section_titles) {
        this.context = context;
        this.tasks = tasks;
        this.section_titles = section_titles;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(tasks.get(position).getId().toString());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) { convertView = inflater.inflate(R.layout.task_list_detail, parent, false); };

        RelativeLayout section_title = (RelativeLayout) convertView.findViewById(R.id.sectionTitle);
        RelativeLayout section_detail = (RelativeLayout) convertView.findViewById(R.id.detail);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView title = (TextView) convertView.findViewById(R.id.section_title);
        ImageView terminated = (ImageView) convertView.findViewById(R.id.terminated);
        TextView contact = (TextView) convertView.findViewById(R.id.task_contact);
        TextView machine = (TextView) convertView.findViewById(R.id.task_machine);
        TextView date = (TextView) convertView.findViewById(R.id.task_date_list);
        TextView time = (TextView) convertView.findViewById(R.id.task_time);

        Task task = (Task) getItem(position);

        if (section_titles.containsKey(position)) {
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            String today_str = date_format.format(new Date());
            String date_str = section_titles.get(position);

            try {
                Date today_date = date_format.parse(today_str);
                Date current_date = date_format.parse(date_str);

                if (current_date.equals(today_date)) {
                    title.setText("Aujourd'hui");
                    title.setTextColor(Color.parseColor("#008000"));
                } else if (current_date.before(today_date)) {
                    title.setText("Passé");
                    title.setTextColor(Color.parseColor("#FF0000"));
                    has_already_passed_title = has_already_passed_title + 1;
                } else {
                    title.setText(section_titles.get(position));
                    title.setTextColor(Color.parseColor("#FF9E13"));
                }

                if(current_date.before(today_date) && has_already_passed_title > 1) {
                    section_title.setVisibility(View.GONE);
                } else {
                    section_title.setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            section_title.setVisibility(View.GONE);
        }

        if(Boolean.parseBoolean(task.getTerminated())) {
            terminated.setImageResource(android.R.drawable.checkbox_on_background);
            lp.setMargins(10, 10, 10, 10);

            if(task.getTime_spend() != "0") {
                time.setText(task.getTime_spend());
                time.setVisibility(View.VISIBLE);
            }
        } else {
            terminated.setImageResource(android.R.drawable.checkbox_off_background);
            lp.setMargins(20, 25, 0, 0);
            terminated.setLayoutParams(lp);

            time.setVisibility(View.GONE);
        }

        contact.setText(task.getContact(context).getFullName());
        machine.setText(task.getMachineCaracteristics());
        date.setText(task.getDisplayDate());

        return convertView;
    }
}
