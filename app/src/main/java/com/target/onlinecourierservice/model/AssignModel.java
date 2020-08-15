package com.target.onlinecourierservice.model;

public class AssignModel {
    private String parcelId;
    private long time;
    private String action;
    private String driverId;

    public AssignModel() {
    }

    public AssignModel(String parcelId, long time, String action, String driverId) {
        this.parcelId = parcelId;
        this.time = time;
        this.action = action;
        this.driverId=driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getParcelId() {
        return parcelId;
    }

    public void setParcelId(String parcelId) {
        this.parcelId = parcelId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
