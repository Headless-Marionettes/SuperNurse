package com.example.supernurse.models;

import java.io.Serializable;

public class EmergencyContact implements Serializable {

    String name;
    String phonenumber;
    String email;
    String address;

    String _id;

    public EmergencyContact(String name, String phonenumber, String email, String address, String _id) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String get_id() {
        return _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
