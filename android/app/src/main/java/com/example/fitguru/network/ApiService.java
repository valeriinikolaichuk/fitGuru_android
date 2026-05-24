package com.example.fitguru.network;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.auth.dto.RefreshRequest;
import com.example.fitguru.auth.dto.RegisterRequest;
import com.example.fitguru.client.dto.AvailableTrainerResponse;
import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.main.dto.ClientResponse;
import com.example.fitguru.main.dto.TrainerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
    @POST("/auth/register")
    Call<LoginResponse> register(@Body RegisterRequest request);

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/auth/refresh")
    Call<LoginResponse> refresh(@Body RefreshRequest request);

    @GET("/trainer/clients")
    Call<List<ClientResponse>> getClients();

    @GET("/client/trainers")
    Call<List<TrainerResponse>> getTrainers();

    @GET("/client/trainers/available")
    Call<List<AvailableTrainerResponse>> getAvailableTrainers();

    @DELETE("/requests/{trainerId}")
    Call<Void> cancelRequest(
            @Path("trainerId") Long trainerId
    );

    @GET("/trainer/requests")
    Call<List<TrainingRequestResponse>> getRequests();

    @POST("/trainer/requests/{id}/accept")
    Call<Void> acceptRequest(
            @Path("id") Long requestId
    );

    @POST("/trainer/requests/{id}/reject")
    Call<Void> rejectRequest(
            @Path("id") Long requestId
    );

    @POST("/requests/{trainerId}")
    Call<Void> sendRequest(
            @Path("trainerId") Long trainerId
    );
}
