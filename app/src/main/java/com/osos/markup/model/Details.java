package com.osos.markup.model;

public class Details {
    double  Alt;
    double Lat;
    double Long;
    String time;



    public Details() {
    }

    public Details(double alt, double lat, double aLong, String time) {
        Alt = alt;
        Lat = lat;
        Long = aLong;
        this.time = time;
    }



    public double getAlt() {
        return Alt;
    }

    public void setAlt(double alt) {
        Alt = alt;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


