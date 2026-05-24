package com.example.fitguru.client.dto;

public class AvailableTrainerResponse {

    private Long id;
    private String name;
    private String phone;

    private String requestStatus; // NONE / PENDING / ACCEPTED


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRequestStatus() { return requestStatus; }
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
