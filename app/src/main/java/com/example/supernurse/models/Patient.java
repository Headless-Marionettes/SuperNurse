package com.example.supernurse.models;

import com.google.gson.annotations.SerializedName;

public class Patient {
  //  @SerializedName("title")
    String name;
    String body;
    long id;

    long userId;

    public Patient() {
    }

    public Patient(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Patient(String name, String body, long id, long userId) {
        this.name = name;
        this.body = body;
        this.id = id;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

