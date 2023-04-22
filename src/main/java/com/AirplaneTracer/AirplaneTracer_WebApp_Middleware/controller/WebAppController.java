package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.controller;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Waypoint;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    @CrossOrigin("http://localhost:3000")
    @PostMapping(path = {"/resultsAirplaneTracer"})
    @ResponseBody
    public Iterable<List<Waypoint>> getWaypoints(@RequestBody List<Integer> flightIds)
    { return this.flightRepository.getWaypoints(flightIds);}

    @CrossOrigin("http://localhost:3000")
    @PostMapping(path = {"/zip"})
    public List<String> zipFlightPlans(@RequestBody List<Integer> flightIds) throws IOException {
        return this.flightRepository.getFlightFiles(flightIds);
    }
}
