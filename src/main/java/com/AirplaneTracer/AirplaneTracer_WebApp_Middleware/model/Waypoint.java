package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

public class Waypoint {

    private float latitude;

    private float longitude;

    public Waypoint(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
