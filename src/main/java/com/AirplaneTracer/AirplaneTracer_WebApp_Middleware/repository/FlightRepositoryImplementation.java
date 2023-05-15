package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.DBUtil;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.*;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightRepositoryImplementation implements FlightRepository {

    Connection connection;

    public FlightRepositoryImplementation() throws SQLException {
        connection = DBUtil.getConnection();
    }

    /**
     * Handles all the possible queries from the front end.
     */
    @Override
    public List<Flight> getFlights(Query query) {

        List<Flight> FlightList = new ArrayList<>();

        ResultSet rs;


        try {

            // ensure the database connection has not disconnected due to inactivity
            if(!connection.isValid(5)){
                connection = DBUtil.getConnection();
            }

            if(query.getFtopDate().equals("")){
                // No information provided ( returns the entire flight table )
                if (query.getFtopFairfield().equals("") && query.getFbottomFairfield().equals("")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight");

                    rs = stmt.executeQuery();

                // Just an arrival airport is provided in the top search field
                } else if (query.getFbottomFairfield().equals("") && query.getFtopArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_airport = ?");

                    stmt.setString(1, query.getFtopFairfield());

                    rs = stmt.executeQuery();

                // Just an arrival airport is provided in the bottom search field
                } else if (query.getFtopFairfield().equals("") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_airport = ?");

                    stmt.setString(1, query.getFbottomFairfield());

                    rs = stmt.executeQuery();

                // Only a departing airport is provided in the top search field
                } else if (query.getFbottomFairfield().equals("") && query.getFtopDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_airport = ?");

                    stmt.setString(1, query.getFtopFairfield());

                    rs = stmt.executeQuery();

                // Only a departing airport is provided in the bottom search field
                } else if (query.getFtopFairfield().equals("") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_airport = ?");

                    stmt.setString(1, query.getFbottomFairfield());

                    rs = stmt.executeQuery();

                // An arrival airport is provided in the top search field and a departing airport is provided in the
                // bottom search field.
                } else if (query.getFtopArrival().equals("Arrival") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE " +
                            "arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, query.getFtopFairfield());
                    stmt.setString(2, query.getFbottomFairfield());


                    rs = stmt.executeQuery();

                // A departing airport is provided in the top search field and an arrival airport is provided in the
                // bottom search field.
                } else if (query.getFtopDeparture().equals("Departure") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE " +
                            "arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, query.getFbottomFairfield());
                    stmt.setString(2, query.getFtopFairfield());


                    rs = stmt.executeQuery();

                // A "both" airfield is provided in top slot. Flights that either arrive or depart from this
                // airport are returned.
                } else {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
                            " arrival_airport = ? OR departure_airport = ?");


                    stmt.setString(1, query.getFtopFairfield());
                    stmt.setString(2, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                }



            } else if(query.getFtopTime().equals("")){

                String datetimeLowerBound = getDateLowerBound(query);
                String datetimeUpperBound = getDateUpperBound(query);

                // Only a date is provided. All flights from the date are returned
                if (query.getFtopFairfield().equals("") && query.getFbottomFairfield().equals("")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE (departure_datetime >= ?" +
                            " AND departure_datetime <= ?) OR ( arrival_datetime >= ? AND arrival_datetime <= ?)");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, datetimeLowerBound);
                    stmt.setString(4, datetimeUpperBound);

                    rs = stmt.executeQuery();

                // An arrival airfield is provided in the top search field with a date criteria.
                } else if (query.getFbottomFairfield().equals("") && query.getFtopArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime >= ?" +
                            "AND arrival_datetime <= ? AND arrival_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();

                // An arrival airport is provided in the bottom search field with a date criteria
                } else if (query.getFtopFairfield().equals("") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime >= ?" +
                            "AND arrival_datetime <= ? AND arrival_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();

                // A departing airport is provided in the top search field with a date criteria
                } else if (query.getFbottomFairfield().equals("") && query.getFtopDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();

                // A departing airport is provided in the bottom search field with a date criteria
                } else if (query.getFtopFairfield().equals("") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();

                // An arrival airport is provided in the top search field and a departing airport in the bottom search
                // field with a date criteria
                } else if (query.getFtopArrival().equals("Arrival") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime >= ?" +
                            "AND arrival_datetime <= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());
                    stmt.setString(4, query.getFbottomFairfield());


                    rs = stmt.executeQuery();

                // A departing airport is provided in the top search field and an arrival airport is provided in the
                // bottom search field with a date criteria
                } else if (query.getFtopDeparture().equals("Departure") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFbottomFairfield());
                    stmt.setString(4, query.getFtopFairfield());


                    rs = stmt.executeQuery();

                // A "both" airfield is provided in the top search field with a date criteria
                } else {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND (arrival_airport = ? OR departure_airport = ?)");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());
                    stmt.setString(4, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                }
            }else {

                String datetime = getDateTime(query);

                // A date and time are provided
                if (query.getFtopFairfield().equals("") && query.getFbottomFairfield().equals("")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);

                    rs = stmt.executeQuery();

                // An arrival airport is provided in the top search field with a date and time
                } else if (query.getFbottomFairfield().equals("") && query.getFtopArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();

                // An arrival airport is provided in the bottom search field with a date and time
                } else if (query.getFtopFairfield().equals("") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();

                // A departure airport is provided in the top search field with a date and time
                } else if (query.getFbottomFairfield().equals("") && query.getFtopDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();

                // A departure airport is provided in the bottom search field with a date and time
                } else if (query.getFtopFairfield().equals("") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();

                // An arrival airport is provided in top search field and a departure airport in the bottom search field
                // with a date and time
                } else if (query.getFtopArrival().equals("Arrival") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());
                    stmt.setString(4, query.getFbottomFairfield());


                    rs = stmt.executeQuery();

                // A departure airport is provided in the top search field and an arrival airport in the bottom search
                // field with a date and time
                } else if (query.getFtopDeparture().equals("Departure") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFbottomFairfield());
                    stmt.setString(4, query.getFtopFairfield());


                    rs = stmt.executeQuery();

                // a "both" airfield is provided with a date and time
                } else {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND (arrival_airport = ? OR departure_airport = ?)");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());
                    stmt.setString(4, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                }
            }

            // Iterate through the flights that matched the search criteria and create flight objects to return
            while(rs.next()){
                int flightId = rs.getInt(1);
                String icao24 = rs.getString(2);
                String callsign = rs.getString(3);
                String departureAirport = rs.getString(6);
                String arrivalAirport = rs.getString(7);
                String departureDateTime = rs.getString(8);
                String arrivalDateTime = rs.getString(9);
                Long duration = rs.getLong(10);
                Long hours = duration / 3600;
                Long minutes = (duration % 3600) / 60;
                Long seconds = duration % 60;
                String durationString = String.format("%02d:%02d:%02d",hours,minutes,seconds);

                Flight flight = new Flight(flightId, icao24, callsign, departureAirport, departureDateTime, arrivalDateTime, arrivalAirport,durationString);
                FlightList.add(flight);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return FlightList;

    }

    /**
     * Iterates through all the flight ids creating a list of waypoints for each id, then returning a list of all
     * the lists of waypoints found.
     */
    @Override
    public List<List<Waypoint>> getWaypoints(List<Integer> flightIds){
        List<List<Waypoint>> waypoints = new ArrayList<>();
        int flightNum = 0;
        for (Integer flightId : flightIds) {
            try {

                if(!connection.isValid(5)){
                    connection = DBUtil.getConnection();
                }

                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Waypoint WHERE flight_id = ? ORDER BY offset_ms");
                stmt.setInt(1, flightId);
                ResultSet rs = stmt.executeQuery();

                waypoints.add(new ArrayList<>());
                while(rs.next()){
                    float latitude = rs.getFloat(2);
                    float longitude = rs.getFloat(3);
                    waypoints.get(flightNum).add(new Waypoint(latitude,longitude));
                }

                flightNum++;


            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }

        return waypoints;
    }

    /**
     * Returns a list of the strings with each string containing all the contents of a flight plan file (fml)
     */
    @Override
    public List<String> getFlightFiles(List<Integer> flightIds){

        // List of files
        List<String> files = new ArrayList<>();

        for (Integer flightId : flightIds) {
            try {

                // ensures the connection is still valid and not disconnected due to inactivity
                if(!connection.isValid(5)){
                    connection = DBUtil.getConnection();
                }

                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Waypoint WHERE flight_id = ? ORDER BY offset_ms");
                stmt.setInt(1, flightId);
                ResultSet rs = stmt.executeQuery();

                List<FmlWaypoint> waypoints = new ArrayList<>();
                while(rs.next()){
                    String flight_id = rs.getString(1);
                    double latitude = rs.getDouble(2);
                    double longitude = rs.getDouble(3);
                    double altitude = rs.getDouble(4);
                    int offset_ms = rs.getInt(5);
                    waypoints.add(new FmlWaypoint(flight_id,longitude,latitude,altitude,offset_ms));
                }

                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Flight WHERE flight_id = ?");
                stmt2.setInt(1, flightId);
                ResultSet rsFlight = stmt2.executeQuery();

                FmlFlight flight;
                while(rsFlight.next()){
                    String departureAirport = rsFlight.getString(6);
                    String arrivalAirport = rsFlight.getString(7);
                    boolean departureContainsComma = departureAirport.contains(",");
                    boolean arrivalContainsComma = arrivalAirport.contains(",");
                    flight = new FmlFlight(flightId.toString(),departureAirport.split(",")[0],arrivalAirport.split(",")[0],waypoints,departureContainsComma,arrivalContainsComma);

                    FmlBuilder fmlBuilder = new FmlBuilder(flight);
                    String fileContents = fmlBuilder.buildFml();
                    files.add(fileContents);
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }

        return files;
    }

    private String getDateTime(Query query){
        String date;
        String time;
        if (query.getFtopTime().equals("")){
            LocalTime localTime = LocalTime.now();
            time = localTime.toString();
        } else{
            time = query.getFtopTime();
        }

        if (query.getFtopDate().equals("")){
            LocalDate localDate = LocalDate.now();
            date = localDate.toString();
        } else{
            date = query.getFtopDate();
        }

        return date + " " + time;
    }


    private String getDateLowerBound(Query query){
        String date;
        String time;
        time = "00:00:00";

        if (query.getFtopDate().equals("")){
            LocalDate localDate = LocalDate.now();
            date = localDate.toString();
        } else{
            date = query.getFtopDate();
        }

        return date + " " + time;
    }

    private String getDateUpperBound(Query query){
        String date;
        String time;
        time = "23:59:59";

        if (query.getFtopDate().equals("")){
            LocalDate localDate = LocalDate.now();
            date = localDate.toString();
        } else{
            date = query.getFtopDate();
        }

        return date + " " + time;
    }
}
