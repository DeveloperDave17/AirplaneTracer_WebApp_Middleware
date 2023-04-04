package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.DBUtil;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_date = ?");
            stmt.setString(1,query.getFtopDate());
            ResultSet rs = stmt.executeQuery();

            System.out.println(query.getFtopDate());

            while(rs.next()){
                int flightId = rs.getInt(1);
                String icao24 = rs.getString(2);
                String departureAirport = rs.getString(6);
                String arrivalAirport = rs.getString(7);
                String departureTime = rs.getString(8);
                String departureDate = rs.getString(9);
                String arrivalTime = rs.getString(10);
                String arrivalDate = rs.getString(11);

                Flight flight = new Flight(flightId, icao24, departureAirport, departureTime, departureDate,
                        arrivalAirport, arrivalTime, arrivalDate);
                FlightList.add(flight);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return FlightList;

    }
}
