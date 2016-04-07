package com.example.appy.technictasks.Models;

/**
 * Created by Emmanuelle on 04/02/2016.
 */
public class Contact {

    private Integer id;
    private String first_name;
    private String last_name;
    private String address;
    private String postcode;
    private String city;
    private String phone_number;


    public Contact(String first_name, String last_name, String address, String postcode, String city, String phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.phone_number = phone_number;
    }

    public Contact() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFullName() {
        return this.getFirst_name() + " " + this.getLast_name();
    }

    public String getFullAdress() { return this.getAddress() + " "+ this.getPostcode() +" "+ this.getCity(); }
}
