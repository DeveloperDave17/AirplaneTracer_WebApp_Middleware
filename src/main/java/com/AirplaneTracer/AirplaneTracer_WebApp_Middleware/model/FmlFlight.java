package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

import java.util.List;

public class FmlFlight {
    // instance variables
    String flightID;
    String departureAirport;
    String arrivalAirport;
    List<FmlWaypoint> waypoints;

    // constructor for when all data is known
    public FmlFlight(String flightID, String departureAirport, String arrivalAirport, List<FmlWaypoint> waypoints){
        this.flightID = flightID;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.waypoints = waypoints;
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
}
