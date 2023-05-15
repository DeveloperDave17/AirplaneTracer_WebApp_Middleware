package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

import java.util.List;

public class FmlFlight {
    // instance variables
    String flightID;
    String departureAirport;
    boolean departureContainsComma;
    String arrivalAirport;
    boolean arrivalContainsComma;

    List<FmlWaypoint> waypoints;

    // constructor for when all data is known
    public FmlFlight(String flightID, String departureAirport, String arrivalAirport, List<FmlWaypoint> waypoints, boolean departureContainsComma, boolean arrivalContainsComma){
        this.flightID = flightID;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.waypoints = waypoints;
        this.departureContainsComma = departureContainsComma;
        this.arrivalContainsComma = arrivalContainsComma;
    }
    // constructor when everything but the waypoint array is known
    public FmlFlight(String flightID, String departureAirport, String arrivalAirport){
        this.flightID = flightID;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    // toString method
    public String toString(){
        return flightID + " | " + departureAirport + " to " + arrivalAirport;
    }

    // method to set waypoints after initialization
    public void setWaypoints(List<FmlWaypoint> waypoints){
        this.waypoints = waypoints;
    }

    public boolean isDepartureContainsComma() {
        return departureContainsComma;
    }

    public void setDepartureContainsComma(boolean departureContainsComma) {
        this.departureContainsComma = departureContainsComma;
    }

    public boolean isArrivalContainsComma() {
        return arrivalContainsComma;
    }

    public void setArrivalContainsComma(boolean arrivalContainsComma) {
        this.arrivalContainsComma = arrivalContainsComma;
    }
}
