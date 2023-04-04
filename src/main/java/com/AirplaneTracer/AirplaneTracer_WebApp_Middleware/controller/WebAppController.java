package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.controller;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepository;

@RestController
public class WebAppController {
    @Autowired
    private FlightRepository flightRepository;

    public WebAppController() {
    }

    @CrossOrigin("http://localhost:3000")
    @PostMapping(
            path = {"/submitAirplaneTracer"}
    )
    @ResponseBody
    public Iterable<Flight> getFlights(@RequestBody Query query) {
        return this.flightRepository.getFlights(query);
    }
}
