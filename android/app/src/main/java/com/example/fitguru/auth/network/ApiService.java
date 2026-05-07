package com.example.fitguru.auth.network;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.auth.dto.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/register")
    Call<Void> register(@Body RegisterRequest request);

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
