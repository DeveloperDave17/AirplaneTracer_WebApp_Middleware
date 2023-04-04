package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware;

public class Query {

    private String ftopFairfield;
    private String ftopDeparture;
    private String ftopArrival;
    private String ftopBoth;
    private String ftopDate;
    private String ftopTime;
    private String fbottomFairfield;
    private String fbottomDeparture;
    private String fbottomArrival;

    public Query(String ftopFairfield, String ftopDeparture, String ftopArrival, String ftopBoth, String ftopDate, String ftopTime, String fbottomFairfield, String fbottomDeparture, String fbottomArrival) {
        this.ftopFairfield = ftopFairfield;
        this.ftopDeparture = ftopDeparture;
        this.ftopArrival = ftopArrival;
        this.ftopBoth = ftopBoth;
        this.ftopDate = ftopDate;
        this.ftopTime = ftopTime;
        this.fbottomFairfield = fbottomFairfield;
        this.fbottomDeparture = fbottomDeparture;
        this.fbottomArrival = fbottomArrival;
    }

    public String getFtopFairfield() {
        return ftopFairfield;
    }

    public void setFtopFairfield(String ftopFairfield) {
        this.ftopFairfield = ftopFairfield;
    }

    public String getFtopDeparture() {
        return ftopDeparture;
    }

    public void setFtopDeparture(String ftopDeparture) {
        this.ftopDeparture = ftopDeparture;
    }

    public String getFtopArrival() {
        return ftopArrival;
    }

    public void setFtopArrival(String ftopArrival) {
        this.ftopArrival = ftopArrival;
    }

    public String getFtopBoth() {
        return ftopBoth;
    }

    public void setFtopBoth(String ftopBoth) {
        this.ftopBoth = ftopBoth;
    }

    public String getFtopDate() {
        return ftopDate;
    }

    public void setFtopDate(String ftopDate) {
        this.ftopDate = ftopDate;
    }

    public String getFtopTime() {
        return ftopTime;
    }

    public void setFtopTime(String ftopTime) {
        this.ftopTime = ftopTime;
    }

    public String getFbottomFairfield() {
        return fbottomFairfield;
    }

    public void setFbottomFairfield(String fbottomFairfield) {
        this.fbottomFairfield = fbottomFairfield;
    }

    public String getFbottomDeparture() {
        return fbottomDeparture;
    }

    public void setFbottomDeparture(String fbottomDeparture) {
        this.fbottomDeparture = fbottomDeparture;
    }

    public String getFbottomArrival() {
        return fbottomArrival;
    }

    public void setFbottomArrival(String fbottomArrival) {
        this.fbottomArrival = fbottomArrival;
    }
}
