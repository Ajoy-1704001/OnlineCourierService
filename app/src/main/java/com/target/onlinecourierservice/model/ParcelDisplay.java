package com.target.onlinecourierservice.model;

public class ParcelDisplay {
    private String ParcelID;
    private String date;
    private String status;

    public ParcelDisplay() {
    }

    public ParcelDisplay(String parcelID, String date, String status) {
        ParcelID = parcelID;
        this.date = date;
        this.status = status;
    }

    public String getParcelID() {
        return ParcelID;
    }

    public void setParcelID(String parcelID) {
        ParcelID = parcelID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
