package com.example.fitguru.auth.dto;

public class LoginResponse {

    public String token;
    public String role;

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
