package com.example.fitguru.network;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.auth.dto.RefreshRequest;
import com.example.fitguru.auth.dto.RegisterRequest;
import com.example.fitguru.client.dto.AvailableTrainerResponse;
import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.main.dto.ClientResponse;
import com.example.fitguru.main.dto.TrainerResponse;
import com.example.fitguru.program.dto.ExerciseResponse;
import com.example.fitguru.program.dto.ProgramCreateRequest;
import com.example.fitguru.program.dto.ProgramDayCreateRequest;
import com.example.fitguru.program.dto.ProgramDayResponse;
import com.example.fitguru.program.dto.ProgramExerciseCreateRequest;
import com.example.fitguru.program.dto.ProgramExerciseResponse;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.program.dto.ProgramUpdateRequest;
import com.example.fitguru.program.dto.ProgramWeekCreateRequest;
import com.example.fitguru.program.dto.ProgramWeekResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("/api/programs/client/{clientId}")
    Call<List<ProgramResponse>> getProgramsByClient(
            @Path("clientId") Long clientId
    );

// -- TrainingRequestRepository

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

// -- ProgramCreateRepository

    @POST("api/programs")
    Call<ProgramResponse> createProgram(
            @Body ProgramCreateRequest request
    );

    @PUT("/api/programs/{id}")
    Call<ProgramResponse> updateProgram(
            @Path("id") Long id,
            @Body ProgramUpdateRequest request
    );

    @GET("/api/programs/{id}") // programs name
    Call<ProgramResponse> getProgram(
            @Path("id") Long id
    );

    @DELETE("/api/programs/{id}")
    Call<Void> deleteProgram(
            @Path("id") Long id
    );

// -- WEEK

    @POST("api/program-weeks")
    Call<ProgramWeekResponse> createWeek(
            @Body ProgramWeekCreateRequest request
    );

    @GET("/api/program-weeks/program/{programId}")
    Call<List<ProgramWeekResponse>> getWeeksByProgram(
            @Path("programId") Long programId
    );

    @DELETE("api/program-weeks/{weekId}")
    Call<Void> deleteWeek(
            @Path("weekId") Long weekId
    );

// -- DAY

    @POST("api/program-days")
    Call<Void> createDay(
            @Body List<ProgramDayCreateRequest> requests
    );

    @GET("api/program-days/week/{weekId}")
    Call<List<ProgramDayResponse>> getDaysByWeek(
            @Path("weekId") Long weekId
    );

    @DELETE("api/program-days/{dayId}")
    Call<Void> deleteDay(
            @Path("dayId") Long dayId
    );

// -- EXERCISE

    @POST("api/program-exercises")
    Call<ProgramExerciseResponse> createExercise(
            @Body ProgramExerciseCreateRequest request
    );

    @GET("api/exercises")
    Call<List<ExerciseResponse>> getExercisesByGroup(
            @Query("muscleGroup") String muscleGroup
    );

    @GET("api/program-exercises/day/{dayId}")
    Call<List<ProgramExerciseResponse>> getExercisesByDay(
            @Path("dayId") Long dayId
    );
}
