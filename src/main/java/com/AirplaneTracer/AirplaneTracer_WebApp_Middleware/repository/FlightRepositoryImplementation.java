package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.DBUtil;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Waypoint;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Flight> getFlights(Query query) {

        List<Flight> FlightList = new ArrayList<>();

        ResultSet rs;


        try {
            if(query.getFtopDate().equals("")){
                if (query.getFtopFairfield().equals("") && query.getFbottomFairfield().equals("")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight");

                    rs = stmt.executeQuery();
                } else if (query.getFbottomFairfield().equals("") && query.getFtopArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_airport = ?");

                    stmt.setString(1, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopFairfield().equals("") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_airport = ?");

                    stmt.setString(1, query.getFbottomFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFbottomFairfield().equals("") && query.getFtopDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_airport = ?");

                    stmt.setString(1, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopFairfield().equals("") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_airport = ?");

                    stmt.setString(1, query.getFbottomFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopArrival().equals("Arrival") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE " +
                            "arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, query.getFtopFairfield());
                    stmt.setString(2, query.getFbottomFairfield());


                    rs = stmt.executeQuery();
                } else if (query.getFtopDeparture().equals("Departure") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE " +
                            "arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, query.getFbottomFairfield());
                    stmt.setString(2, query.getFtopFairfield());


                    rs = stmt.executeQuery();
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

                if (query.getFtopFairfield().equals("") && query.getFbottomFairfield().equals("")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE (departure_datetime >= ?" +
                            " AND departure_datetime <= ?) OR ( arrival_datetime >= ? AND arrival_datetime <= ?)");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, datetimeLowerBound);
                    stmt.setString(4, datetimeUpperBound);

                    rs = stmt.executeQuery();
                } else if (query.getFbottomFairfield().equals("") && query.getFtopArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime >= ?" +
                            "AND arrival_datetime <= ? AND arrival_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopFairfield().equals("") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime >= ?" +
                            "AND arrival_datetime <= ? AND arrival_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFbottomFairfield().equals("") && query.getFtopDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopFairfield().equals("") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopArrival().equals("Arrival") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime >= ?" +
                            "AND arrival_datetime <= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFtopFairfield());
                    stmt.setString(4, query.getFbottomFairfield());


                    rs = stmt.executeQuery();
                } else if (query.getFtopDeparture().equals("Departure") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime >= ?" +
                            "AND departure_datetime <= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetimeLowerBound);
                    stmt.setString(2, datetimeUpperBound);
                    stmt.setString(3, query.getFbottomFairfield());
                    stmt.setString(4, query.getFtopFairfield());


                    rs = stmt.executeQuery();
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

                if (query.getFtopFairfield().equals("") && query.getFbottomFairfield().equals("")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);

                    rs = stmt.executeQuery();
                } else if (query.getFbottomFairfield().equals("") && query.getFtopArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopFairfield().equals("") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFbottomFairfield().equals("") && query.getFtopDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopFairfield().equals("") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFbottomFairfield());

                    rs = stmt.executeQuery();
                } else if (query.getFtopArrival().equals("Arrival") && query.getFbottomDeparture().equals("Departure")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFtopFairfield());
                    stmt.setString(4, query.getFbottomFairfield());


                    rs = stmt.executeQuery();
                } else if (query.getFtopDeparture().equals("Departure") && query.getFbottomArrival().equals("Arrival")) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= ?" +
                            "AND arrival_datetime >= ? AND arrival_airport = ? AND departure_airport = ?");

                    stmt.setString(1, datetime);
                    stmt.setString(2, datetime);
                    stmt.setString(3, query.getFbottomFairfield());
                    stmt.setString(4, query.getFtopFairfield());


                    rs = stmt.executeQuery();
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

    @Override
    public List<List<Waypoint>> getWaypoints(List<Integer> flightIds){
        List<List<Waypoint>> waypoints = new ArrayList<>();
        int flightNum = 0;
        for (Integer flightId : flightIds) {
            try {
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
