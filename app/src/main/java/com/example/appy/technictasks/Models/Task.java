package com.example.appy.technictasks.Models;

import android.content.Context;

import com.example.appy.technictasks.BDD.DB;

import java.io.Serializable;

/**
 * Created by Emmanuelle on 28/01/2016.
 */
public class Task implements Serializable {

    // A séparer !!!

    private Integer id;
    private Customer customer;
    private User user;
    private String machine_type;
    private String machine_brand;
    private String problem;
    private String date;
    private String terminated;
    private String time_spend;
    private String comment;

    // chronométrer le temps
    // ajouter image type de machine

    public Task(Customer customer, String date, String machine_type, String machine_brand, String problem, User user) {
        this.customer = customer;
        this.date = date;
        this.machine_type = machine_type;
        this.machine_brand = machine_brand;
        this.problem = problem;
        this.user = user;
        this.terminated = "false";
        this.time_spend = "0";
    }

    public Task() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMachine_brand() {
        return machine_brand;
    }

    public void setMachine_brand(String machine_brand) {
        this.machine_brand = machine_brand;
    }

    public String getMachine_type() {
        return machine_type;
    }

    public void setMachine_type(String machine_type) {
        this.machine_type = machine_type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDate() {
        return date;
    }

    public String getDisplayDate() {
        return getDate().replace(" ", "\n\n");
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTerminated() {
        return terminated;
    }

    public void setTerminated(String terminated) {
        this.terminated = terminated;
    }

    public String getTime_spend() {
        return time_spend;
    }

    public void setTime_spend(String time_spend) {
        this.time_spend = time_spend;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMachineCaracteristics() {
        return this.getMachine_brand() + " " + this.getMachine_type();
    }

    public Contact getContact(Context context) {
        DB base = new DB(context);
        base.open();
        Contact contact = base.getContactById(this.getCustomer().getContact_id());
        base.close();
        return contact;
    }
}
