package com.osos.markup.model;

public class Details {
    float  Alt;
    float Lat;
    float Long;
    String time;



    public Details(){

}
    public Details(float toString, float toString1, float toString2, String toString3) {
        Alt=toString;
        Lat=toString1;
        Long=toString2;
        time=toString3;
    }

    public float getAlt() {
        return Alt;
    }

    public void setAlt(float alt) {
        Alt = alt;
    }

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLong() {
        return Long;
    }

    public void setLong(float aLong) {
        Long = aLong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
