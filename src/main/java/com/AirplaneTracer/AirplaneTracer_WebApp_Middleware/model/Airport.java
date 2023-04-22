package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

/*
 * Class to represent an airport mainly
 * String name (Los Angeles International Airport)
 * identifier (KLAX)
 * Longitude (Decimal lon)
 * Latitude (Decimal lat)
 * Altitude (in feet)
 */
public class Airport{
    String name;
    String ident;
    double lon;
    double lat;
    double alt;

    public Airport(String name, String ident, double lon, double lat){
        this.name = name;
        this.ident = ident;
        this.lon = lon;
        this.lat = lat;
        this.alt = 0;
    }

    public Airport(String name, String ident, double lon, double lat, double alt){
        this.name = name;
        this.ident = ident;
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
    }

    public Airport(String name){
        this.name = name;
    }

    // toString method
    public String toString(){
        return name + " | " + ident + " | [" + lon + ", " + lat + " ] | " + alt;
    }
}
