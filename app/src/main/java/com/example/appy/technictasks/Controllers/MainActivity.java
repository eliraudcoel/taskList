package com.example.appy.technictasks.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appy.technictasks.BDD.DB;
import com.example.appy.technictasks.Models.User;
import com.example.appy.technictasks.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button login_btn;
    TextView login, pwd;
    public User current_user = null;
    public static DB base;
    SharedPreferences pref;
    String current_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getPreferences(Activity.MODE_PRIVATE);
        current_token = pref.getString("UserToken", null);

        if(current_token != null) {
            current_user = getCurrentUser();
            goToList();
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
            base = new DB(this);
            base.open();
            base.updateUser(current_user.getId(), current_user);
            base.close();

            pref.edit().putString("UserToken", token()).commit();

            return true;
        } else {
            return false;
        }
    }

    private User getCurrentUser() {
        base = new DB(this);
        base.open();
        User user;

        if(current_token != null) {
            user = base.getUserByToken(current_token);
        } else {
             user = base.getUserByLogin(login.getText().toString(), pwd.getText().toString());
            // User user = base.getUserByLogin("paul", "test"); // uniquement test
        }

        base.close();
        return user;
    }

    private String token() {
        String token = "";
        Random rand = new Random();
        int numLetters = 10;
        String randomLetters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String date_str = new SimpleDateFormat("yyyyMMdd").format(new Date());
        token += date_str;

        for (int n=0; n<numLetters; n++) {
            token += randomLetters.charAt(rand.nextInt(randomLetters.length()));
        }
        token += current_user.getLogin();

        return token;
    }

    private void logIn() {
        if (checkIdentifiant() == true) { goToList(); }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Votre login ou mot de passe est faux.", 10);
            toast.show();
        }
    }

    private void goToList() {
        Intent intent = new Intent(MainActivity.this, ListTasks.class);
        intent.putExtra("current_user_id", current_user.getId()); // à vérifier => serializable
        startActivity(intent);
    }
}
