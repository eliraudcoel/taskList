package com.example.appy.technictasks.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.Models.User;
import com.example.appy.technictasks.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button login_btn;
    TextView login, pwd;
    public User current_user = null;
    public static DB base;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("session", Activity.MODE_PRIVATE);

        // If current_session (token in sharedpreference)
        if(getCurrentToken() != null) {
            current_user = getCurrentUser();
            if (current_user != null) { goToList(); }
        }

        setContentView(R.layout.activity_main);

        login_btn = (Button) findViewById(R.id.log_btn);
        login = (TextView) findViewById(R.id.login);
        pwd = (TextView) findViewById(R.id.password);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private boolean checkIdentifiant() {
       current_user = getCurrentUser();

        if(current_user != null) {
            setToken();

            base = new DB(this);
            base.open();
            base.updateUser(current_user.getId(), current_user);
            base.close();

            return true;
        } else {
            return false;
        }
    }

    private User getCurrentUser() {
        base = new DB(this);
        base.open();
        User user;

        if(getCurrentToken() != null) {
            user = base.getUserByToken(getCurrentToken());
        } else {
            user = base.getUserByLogin(login.getText().toString().trim(), pwd.getText().toString());
        }

        base.close();
        return user;
    }

    private void logIn() {
        if(getCurrentToken() != null) { removeToken(); } // Remove stocked token

        if (checkIdentifiant() == true) {
            goToList();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Votre login ou mot de passe est faux.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void goToList() {
        Intent intent = new Intent(MainActivity.this, ListTasks.class);
        intent.putExtra("current_user_id", current_user.getId());
        startActivity(intent);
    }

    private String getCurrentToken() {
        return pref.getString("UserToken", null);
    }

    private void removeToken() {
        pref.edit().remove("UserToken").commit();
    }

    private void setToken() {
        pref.edit().putString("UserToken", getNewToken()).commit();
    }

    private String getNewToken() {
        String token = "";
        Random rand = new Random();
        int numLetters = 10;
        String randomLetters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String date_str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        token += date_str;

        for (int n=0; n<numLetters; n++) {
            token += randomLetters.charAt(rand.nextInt(randomLetters.length()));
        }
        token += current_user.getLogin();

        return token;
    }
}
