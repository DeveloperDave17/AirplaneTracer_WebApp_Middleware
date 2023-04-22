package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

public class FmlWaypoint {
    // instance variables
    String flightID;
    double longitude; // x
    double latitude; // y
    double altitude; // z
    int offset_ms; // time in milliseconds from start of flight

    // construct a waypoint with all known variables
    public FmlWaypoint(String flightID, double longitude, double latitude, double altitude, int offset_ms) {
        this.flightID = flightID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.offset_ms = offset_ms;
    }

    // construct a waypoint with just flightID
    public FmlWaypoint(String flightID) {
        this.flightID = flightID;
    }

    // to string method
    public String toString(){
        return flightID + " | [ " + longitude + ", " + latitude + ", " + altitude + " ] | " + offset_ms;
    }
}
