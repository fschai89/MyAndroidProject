package com.fschai.myandroidproject.model;

import org.json.JSONObject;

import java.io.Serializable;

public class Contact implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Contact() {
    }

    public Contact(String id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public void parse(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.firstName = jsonObject.optString("firstName");
        this.lastName = jsonObject.optString("lastName");
        this.email = jsonObject.optString("email");
        this.phone = jsonObject.optString("phone");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

