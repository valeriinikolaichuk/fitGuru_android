package com.example.fitguru.network;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.auth.dto.RefreshRequest;
import com.example.fitguru.auth.dto.RegisterRequest;
import com.example.fitguru.client.dto.AvailableTrainerResponse;
import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.main.dto.ClientResponse;
import com.example.fitguru.main.dto.TrainerResponse;
import com.example.fitguru.program.dto.ProgramCreateRequest;

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
    Call<List<ClientResponse>> getClients(); // Connected clients

    @GET("/client/trainers")
    Call<List<TrainerResponse>> getTrainers(); // Connected trainers

    @GET("/client/trainers/available")
    Call<List<AvailableTrainerResponse>> getAvailableTrainers(); // available for requests

    @POST("/requests/{trainerId}") // request to the trainer
    Call<Void> sendRequest(
            @Path("trainerId") Long trainerId
    );

    @DELETE("/requests/{trainerId}") //→ cancel
    Call<Void> cancelRequest(
            @Path("trainerId") Long trainerId
    );

    @GET("/requests/trainer") //→ clients requests sent
    Call<List<TrainingRequestResponse>> getRequests();

    @POST("/requests/{id}/accept") //→ accept
    Call<Void> acceptRequest(
            @Path("id") Long requestId
    );

    @POST("/requests/{id}/reject") //→ reject
    Call<Void> rejectRequest(
            @Path("id") Long requestId
    );

    /*

    @POST("api/programs")
    Call<ProgramResponse> createProgram(
            @Body ProgramCreateRequest request
    );
     */
}
