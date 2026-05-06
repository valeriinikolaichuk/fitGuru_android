package com.example.fitguru;

public class RegisterRequest {
    private String name;
    private String phone;
    private String password;
    private String role;

    public RegisterRequest(String name, String phone, String password, String role) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
}
