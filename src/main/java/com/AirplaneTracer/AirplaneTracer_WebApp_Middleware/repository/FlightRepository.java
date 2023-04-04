package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository{

    public List<Flight> getFlights(Query query);

}
