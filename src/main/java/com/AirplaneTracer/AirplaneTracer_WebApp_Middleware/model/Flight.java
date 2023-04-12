package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

public class Flight {

    private int flightId;
    private String icao24;
    private String callsign;
    private String departureAirport;
    private String departureDateTime;
    private String arrivalDateTime;
    private String arrivalAirport;
    private String flightDuration;

    public Flight(int flightId, String icao24, String callsign, String departureAirport, String departureDateTime, String arrivalDateTime,
                  String arrivalAirport, String flightDuration) {
        this.flightId = flightId;
        this.icao24 = icao24;
        this.callsign = callsign;
        this.departureAirport = departureAirport;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.arrivalAirport = arrivalAirport;
        this.flightDuration = flightDuration;
    }

    public int getFlightId(){return flightId;}
    public void setFlightId(int flightId){this.flightId = flightId;}

    public String getIcao24(){return icao24;}
    public void setIcao24(String icao24){this.icao24 = icao24;}

    public void setCallsign(String callsign){this.callsign = callsign;}
    public String getCallsign(){return callsign;}

    public String getDepartureAirport(){return departureAirport;}
    public void setDepartureAirport(String departureAirport){this.departureAirport = departureAirport;}

    public String getDepartureDateTime(){return departureDateTime;}
    public void setDepartureDateTime(String departureDateTime){this.departureDateTime = departureDateTime;}

    public String getArrivalAirport(){return arrivalAirport;}
    public void setArrivalAirport(String arrivalAirport){this.arrivalAirport = arrivalAirport;}

    public String getArrivalDateTime(){return arrivalDateTime;}
    public void setArrivalDateTime(String arrivalDateTime){this.arrivalDateTime = arrivalDateTime;}

    public String getFlightDuration() {return flightDuration;}
    public void setFlightDuration(String flightDuration) {this.flightDuration = flightDuration;}
}
