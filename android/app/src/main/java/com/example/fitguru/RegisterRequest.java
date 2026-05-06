package com.example.fitguru;

public class RegisterRequest {
    private String name;
    private String phone;
    private String password;

    public RegisterRequest(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }
}
