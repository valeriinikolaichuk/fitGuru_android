package com.example.fitguru.auth.dto;

public class LoginRequest {
    String phone;
    String password;

    public LoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
