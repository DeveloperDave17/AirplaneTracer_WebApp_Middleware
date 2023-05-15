package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Waypoint;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

/**
 * An interface the springboot will generate the corresponding class for deriving all the implementation of additional
 * methods added from the FlightRepositoryImplementation class
 */
@Repository
public interface FlightRepository{

    public List<Flight> getFlights(Query query);

    public List<List<Waypoint>> getWaypoints(List<Integer> flightIds);

    public List<String> getFlightFiles(List<Integer> flightIds);

}
