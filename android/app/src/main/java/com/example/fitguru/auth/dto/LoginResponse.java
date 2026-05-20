package com.example.fitguru.auth.dto;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    public String role;

    public LoginResponse(
            String accessToken,
            String refreshToken,
            String role
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getRole() {
        return role;
    }
}
