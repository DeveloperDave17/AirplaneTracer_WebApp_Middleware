package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.controller;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Waypoint;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    @PostMapping(path = {"/zip"}, produces = {"application/zip"})
    public void zipFlightPlans(HttpServletResponse response, @RequestBody List<Integer> flightIds) throws IOException {
        List<String> fileStrings =  this.flightRepository.getFlightFiles(flightIds);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"flights.zip\"");

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for(int i = 0; i < fileStrings.size(); i++) {
            byte[] buf = fileStrings.get(i).getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
            zipOutputStream.putNextEntry(new ZipEntry(flightIds.get(i) + ".fml"));
            IOUtils.copy(byteArrayInputStream,zipOutputStream);
            byteArrayInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
    }
}
