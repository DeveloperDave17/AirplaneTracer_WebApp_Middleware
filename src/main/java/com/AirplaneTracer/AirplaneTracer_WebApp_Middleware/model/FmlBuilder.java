package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FmlBuilder {
    static final String CYCLENUMBER = "2303";
    // instance variables
    FmlFlight flight;

    // constructors
    public FmlBuilder(FmlFlight flight){
        this.flight = flight;
    }

    // to string method
    public String toString(){
        return "FMLBuilder for " + flight.toString();
    }

    // setFlight to set flight if you decide you want to do that I guess
    public void setFlight(FmlFlight flight){
        this.flight = flight;
    }

    public String buildFml(){
        /*
         * Build assorted components
         */
        Airport departureAirport = buildAirport(flight.departureAirport, flight.isDepartureContainsComma());
        Airport arrivalAirport = buildAirport(flight.arrivalAirport, flight.isArrivalContainsComma());

        // build arrayList of waypoint strings
        ArrayList<String> waypointsAsString = new ArrayList<>();
        // add departure airport waypoint
        waypointsAsString.add(buildDepartureWaypoint(departureAirport));
        // add all the other waypoints
        double waypointRatio = flight.waypoints.size()/98.0;
        for(int i = 0; i < 97; i++){
            String temp = buildWaypoint(
                    flight.waypoints.get((int)waypointRatio * i),
                    i + 1);
            waypointsAsString.add(temp);
        }
        // add last custom waypoint
        waypointsAsString.add(buildWaypoint(flight.waypoints.get(flight.waypoints.size()-1), 98));
        // add arrival airport waypoint
        waypointsAsString.add(buildArrivalWaypoint(arrivalAirport));
        /*
         * Put it all together
         */
        String fmlAsString = "";

        // ### build starting block
        // first two lines
        fmlAsString = fmlAsString + "I\n1100 Version\n";
        // cycle
        fmlAsString = fmlAsString + "CYCLE " + CYCLENUMBER + "\n";
        // departing airport
        fmlAsString = fmlAsString + "ADEP " + departureAirport.ident + "\n";
        // arrival airport
        fmlAsString = fmlAsString + "ADES " + arrivalAirport.ident + "\n";
        // number of waypoints
        fmlAsString = fmlAsString + "NUMENR " + waypointsAsString.size() + "\n";
        // add all the waypoints
        for(String waypoint : waypointsAsString){
            fmlAsString = fmlAsString + waypoint;
        }

        return fmlAsString;
    }

    //supporting functions


    /*
     * builds an airport object from the csv
     */
    private Airport buildAirport(String name, boolean containsCommas) {
        String[] splitLine = new String[18];
        // parse csv to get the line we want
        try(Scanner scanner = new Scanner(ResourceUtils.getFile("classpath:airportsClosedDeleted.csv"))){
            //Read line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                splitLine = line.split(",");
                if(splitLine[3].contains(name)){
                    if(!containsCommas) {
                        // grab variables from the line
                        String ident = splitLine[1];
                        double alt = 0;
                        //Double.parseDouble(splitLine[6]);
                        double lon = Double.parseDouble(splitLine[5]);
                        double lat = Double.parseDouble(splitLine[4]);
                        // create and return the Airport
                        return new Airport(name, ident, lon, lat, alt);
                    } else {
                        // grab variables from the line
                        String ident = splitLine[1];
                        double alt = 0;
                        //Double.parseDouble(splitLine[6]);
                        double lon = Double.parseDouble(splitLine[6]);
                        double lat = Double.parseDouble(splitLine[5]);
                        // create and return the Airport
                        return new Airport(name, ident, lon, lat, alt);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //28 SV001 DRCT 0 42.006856 -117.744873
    public String buildWaypoint(FmlWaypoint waypoint, int waypointIdNumber){
        String id = "";
        if(waypointIdNumber < 10){
            id = "SV00" + waypointIdNumber;
        }
        else if (waypointIdNumber < 100){
            id = "SV0" + waypointIdNumber;
        }
        // this case should never happen for a real flight plan, but throw it in
        else if (waypointIdNumber < 1000){
            id = "SV" + waypointIdNumber;
        }
        String waypointString =
                "28 " + id + " DRCT " + waypoint.altitude * 3.28084
                        + " " + waypoint.latitude + " " + waypoint.longitude + "\n";
        return waypointString;
    }

    //1 KCUB ADEP 0.000000 33.970470 -80.995247
    public String buildDepartureWaypoint(Airport airport){
        String airportString =
                "1 " + airport.ident + " ADEP " + airport.alt + " " + airport.lat + " " + airport.lon + "\n";
        return airportString;
    }

    //1 KCUB ADES 0.000000 33.970470 -80.995247
    public String buildArrivalWaypoint(Airport airport){
        String airportString =
                "1 " + airport.ident + " ADES " + airport.alt + " " + airport.lat + " " + airport.lon;
        return airportString;
    }
}
