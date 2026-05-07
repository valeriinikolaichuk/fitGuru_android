package com.example.fitguru.auth;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;

import retrofit2.Callback;

public class AuthRepository {
    private final ApiService api;

    public AuthRepository(ApiService api) {
        this.api = api;
    }

    public void login(LoginRequest request, Callback<LoginResponse> callback) {
        api.login(request).enqueue(callback);
    }
}
