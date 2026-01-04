package com.example.locationcaftan.model;

public class ReservationRequest {
    public String name;
    public String phone;
    public int caftan_id;

    public ReservationRequest(String name, String phone, int caftan_id) {
        this.name = name;
        this.phone = phone;
        this.caftan_id = caftan_id;
    }
}
