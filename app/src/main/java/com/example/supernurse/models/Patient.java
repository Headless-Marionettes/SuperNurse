package com.example.supernurse.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Patient implements Serializable {
    //  @SerializedName("title")
    String first_name;
    String last_name;


    String weight;
    String height;
    String date_of_birth;
    String room;

    EmergencyContact emergency_contact;

    String _id;


    public Patient() {
    }

    public Patient(String first_name, String last_name, String weight, String height, String date_of_birth, String room, EmergencyContact emergencyContact, String _id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.weight = weight;
        this.height = height;
        this.date_of_birth = date_of_birth;
        this.room = room;
        this.emergency_contact = emergencyContact;
        this._id = _id;
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


    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getRoom() {
        return room;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public EmergencyContact getEmergencyContact() {
        return emergency_contact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergency_contact = emergencyContact;
    }
}

