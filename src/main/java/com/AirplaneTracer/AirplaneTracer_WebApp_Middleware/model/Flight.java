package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model;

public class Flight {

    private int flightId;
    private String icao24;
    private String departureAirport;
    private String departureTime;
    private String departureDate;
    private String arrivalAirport;
    private String arrivalTime;
    private String arrivalDate;

    public Flight(int flightId, String icao24, String departureAirport, String departureTime, String departureDate,
                  String arrivalAirport, String arrivalTime, String arrivalDate) {
        this.flightId = flightId;
        this.icao24 = icao24;
        this.departureAirport = departureAirport;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.arrivalAirport = arrivalAirport;
        this.arrivalTime = arrivalTime;
        this.arrivalDate = arrivalDate;
    }

    public int getFlightId(){return flightId;}
    public void setFlightId(int flightId){this.flightId = flightId;}

    public String getIcao24(){return icao24;}
    public void setIcao24(String icao24){this.icao24 = icao24;}

    public String getDepartureAirport(){return departureAirport;}
    public void setDepartureAirport(String departureAirport){this.departureAirport = departureAirport;}

    public String getDepartureTime(){return departureTime;}
    public void setDepartureTime(String departureTime){this.departureTime = departureTime;}

    public String getDepartureDate(){return departureDate;}
    public void setDepartureDate(String departureDate){this.departureDate = departureDate;}

    public String getArrivalAirport(){return arrivalAirport;}
    public void setArrivalAirport(String arrivalAirport){this.arrivalAirport = arrivalAirport;}

    public String getArrivalTime(){return arrivalTime;}
    public void setArrivalTime(String arrivalTime){this.arrivalTime = arrivalTime;}

    public String getArrivalDate(){return arrivalDate;}
    public void setArrivalDate(String arrivalDate){this.arrivalDate = arrivalDate;}


}
