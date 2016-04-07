package com.example.appy.technictasks.Models;

import android.content.Context;

import com.example.appy.technictasks.BDD.DB;

/**
 * Created by Emmanuelle on 04/02/2016.
 */
public class Customer {

    private Integer id;
    private Integer contact_id;

    public Customer(Integer contact_id) {
        this.contact_id = contact_id;
    }

    public Customer() {}

    public Contact getContact(Context context) {
        DB base = new DB(context);
        base.open();
        Contact contact = base.getContactById(this.getContact_id());
        base.close();
        return contact;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
