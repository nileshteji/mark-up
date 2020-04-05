package com.osos.markup.model;

public class Details {
    String  Alt;
    String Lat;
    String Long;
    String time;

    public Details(String toString, String toString1, String toString2, String toString3) {
        Alt=toString;
        Lat=toString1;
        Long=toString2;
        time=toString3;
    }

    public String getAlt() {
        return Alt;
    }

    public void setAlt(String alt) {
        Alt = alt;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
