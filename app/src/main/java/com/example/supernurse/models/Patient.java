package com.example.supernurse.models;

import com.google.gson.annotations.SerializedName;

public class Patient {

    //  @SerializedName("title")
    String first_name;
    String last_name;
    String _id;

    public Patient(String first_name, String last_name, String _id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this._id = _id;
    }

    public Patient() {
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

