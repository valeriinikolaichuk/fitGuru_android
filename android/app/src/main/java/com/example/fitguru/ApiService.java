package com.example.fitguru;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/register")
    Call<Void> register(@Body RegisterRequest request);
}
