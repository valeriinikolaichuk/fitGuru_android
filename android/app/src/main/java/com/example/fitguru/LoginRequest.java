package com.example.fitguru;

public class LoginRequest {
    String phone;
    String password;

    public LoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
