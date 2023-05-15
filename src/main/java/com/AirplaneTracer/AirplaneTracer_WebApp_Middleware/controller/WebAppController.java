package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.controller;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.Query;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Waypoint;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class WebAppController {
    @Autowired
    private FlightRepository flightRepository;

    public WebAppController() {
    }

    /**
     * Takes a query object from a post request and returns a list of all the flights that match the criteria
     * @param query all the search criteria sent from the front end
     */
    @CrossOrigin("http://moxie.cs.oswego.edu:51262")
    @PostMapping(
            path = {"/submitAirplaneTracer"}
    )
    @ResponseBody
    public Iterable<Flight> getFlights(@RequestBody Query query) {
        return this.flightRepository.getFlights(query);
    }

    /**
     * Takes a list of flight ids from a post request and returns a list of groups of waypoints with each group
     * corresponding to a flight id.
     */
    @CrossOrigin("http://moxie.cs.oswego.edu:51262")
    @PostMapping(path = {"/resultsAirplaneTracer"})
    @ResponseBody
    public Iterable<List<Waypoint>> getWaypoints(@RequestBody List<Integer> flightIds)
    { return this.flightRepository.getWaypoints(flightIds);}

    /**
     * Takes a list of flight ids and returns a zip file that contains all the flight plans that correspond
     * to flight ids received.
     */
    @CrossOrigin("http://moxie.cs.oswego.edu:51262")
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
