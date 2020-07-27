package com.target.onlinecourierservice.model;

public class CartDisplayLIst {
    private String Id;
    private String date;
    private String status;

    public CartDisplayLIst() {
    }

    public CartDisplayLIst(String id, String date, String status) {
        Id = id;
        this.date = date;
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
