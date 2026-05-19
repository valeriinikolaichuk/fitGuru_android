package com.fitguru.backend.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    public String role;

    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
