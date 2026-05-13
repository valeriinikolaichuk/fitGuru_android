package com.example.fitguru.network;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.auth.dto.RegisterRequest;
import com.example.fitguru.trainer.dto.ClientResponse;
import com.example.fitguru.trainer.dto.TrainerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import retrofit2.http.GET;
import retrofit2.http.Header;
import java.util.List;

public interface ApiService {
    @POST("/auth/register")
    Call<LoginResponse> register(@Body RegisterRequest request);

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("/trainer/clients")
    Call<List<ClientResponse>> getClients(
            @Header("Authorization") String token
    );

    @GET("/client/trainers")
    Call<List<TrainerResponse>> getTrainers(
            @Header("Authorization") String token
    );
}
