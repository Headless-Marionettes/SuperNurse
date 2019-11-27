package com.example.supernurse.models;

public class Record {

    String date;
    String blood_pressure;
    String respiratory_rate;
    String blood_oxygen_level;
    String heart_beat_rate;

    public Record() {
    }

    public Record(String date, String blood_pressure, String respiratory_rate, String blood_oxygen_level, String heart_beat_rate) {
        this.date = date;
        this.blood_pressure = blood_pressure;
        this.respiratory_rate = respiratory_rate;
        this.blood_oxygen_level = blood_oxygen_level;
        this.heart_beat_rate = heart_beat_rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(String blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public String getRespiratory_rate() {
        return respiratory_rate;
    }

    public void setRespiratory_rate(String respiratory_rate) {
        this.respiratory_rate = respiratory_rate;
    }

    public String getBlood_oxygen_level() {
        return blood_oxygen_level;
    }

    public void setBlood_oxygen_level(String blood_oxygen_level) {
        this.blood_oxygen_level = blood_oxygen_level;
    }

    public String getHeart_beat_rate() {
        return heart_beat_rate;
    }

    public void setHeart_beat_rate(String heart_beat_rate) {
        this.heart_beat_rate = heart_beat_rate;
    }
}
