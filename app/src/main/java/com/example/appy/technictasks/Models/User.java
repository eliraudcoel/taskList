package com.example.appy.technictasks.Models;

import android.content.Context;

import com.example.appy.technictasks.BDD.DB;

import java.util.ArrayList;

/**
 * Created by Emmanuelle on 04/02/2016.
 */
public class User {

    private Integer id;
    private String login;
    private String email;
    private String password;
    private Integer contact_id;
    private String token;
    private ArrayList<Task> tasks;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
    }

    public void addTask(Task task) { this.tasks.add(task); }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Contact getContact(Context context) {
        DB base = new DB(context);
        base.open();
        Contact contact = base.getContactById(this.getContact_id());
        base.close();
        return contact;
    }

    public ArrayList<Task> getTasks(Context context) {

        DB base = new DB(context);
        base.open();
        ArrayList<Task> tasks = base.getUserTasks(this.getId());
        base.close();

        return tasks;
    }
}
