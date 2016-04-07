package com.example.appy.technictasks.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.Models.Task;
import com.example.appy.technictasks.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TaskDetail extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DB base = new DB(getBaseContext());
        base.open();
        task = base.getTaskById(getIntent().getIntExtra("task_id", 0));
        base.close();

        TextView contact_task_name = (TextView) findViewById(R.id.contact_task_name);
        TextView machine_name = (TextView) findViewById(R.id.machine_name);
        TextView problem_text = (TextView) findViewById(R.id.problem_text);
        TextView task_date = (TextView) findViewById(R.id.task_date);
        TextView comment_text = (TextView) findViewById(R.id.comment_text);
        TextView time_spend_text = (TextView) findViewById(R.id.spend_time_text);
        ImageButton call_btn = (ImageButton) findViewById(R.id.call_btn);
        ImageView date_img = (ImageView) findViewById(R.id.date_img);
        ImageView pb_img = (ImageView) findViewById(R.id.pb_img);
        ImageView infos_img = (ImageView) findViewById(R.id.infos_img);
        Button edit_task = (Button) findViewById(R.id.edit_task);
        RelativeLayout infos_layout = (RelativeLayout) findViewById(R.id.infos);

        contact_task_name.setText(task.getCustomer().getContact(getApplicationContext()).getFullName());
        machine_name.setText(task.getMachineCaracteristics());
        problem_text.setText(task.getProblem());
        task_date.setText(task.getDate());
        // comment_text.setText(task.getComment());
        time_spend_text.setText(task.getTime_spend());

        // Change color of imageViews
        // int orange_color = Color.parseColor("#FF9E13");
        int red_color = Color.parseColor("#DD4B39");
        call_btn.setColorFilter(red_color);
        date_img.setColorFilter(red_color);
        pb_img.setColorFilter(red_color);
        infos_img.setColorFilter(red_color);

        if(Boolean.parseBoolean(task.getTerminated())) {
            infos_layout.setVisibility(View.VISIBLE);
            edit_task.setVisibility(View.GONE);
        }

        // Buttons Onclick
        call_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + task.getContact(getBaseContext()).getPhone_number()));
                if (ActivityCompat.checkSelfPermission(TaskDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }

        });

        edit_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chrono Page => not working
                // Intent chronoIntent = new Intent(TaskDetail.this, ChronometerTask.class);

                Intent chronoIntent = new Intent(TaskDetail.this, EditTask.class);
                chronoIntent.putExtra("current_user_id", getIntent().getIntExtra("current_user_id", 0));
                chronoIntent.putExtra("task_id", task.getId());
                startActivity(chronoIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // voir avec l'addresse

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            String task_adress = task.getContact(getBaseContext()).getFullAdress();
            addresses = geocoder.getFromLocationName(task_adress, 1);
            Address address = addresses.get(0);
            double longitude = address.getLongitude();
            double latitude = address.getLatitude();

            LatLng adressCoordinate = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions().position(adressCoordinate).title(task.getContact(getApplicationContext()).getFullName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(adressCoordinate));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Uri gmmIntentUri = Uri.parse("geo:" + latLng.latitude + "," + latLng.longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
