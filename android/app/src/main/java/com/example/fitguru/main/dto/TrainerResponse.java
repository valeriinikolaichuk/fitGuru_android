package com.example.fitguru.main.dto;

public class TrainerResponse {
    public Long id;
    public String name;
    public String phone;

    public Long trainerClientId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Long getTrainerClientId() { return trainerClientId; }
}
