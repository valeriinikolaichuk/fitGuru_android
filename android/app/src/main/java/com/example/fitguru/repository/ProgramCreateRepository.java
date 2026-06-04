package com.example.fitguru.repository;

import com.example.fitguru.network.ApiService;
import com.example.fitguru.program.dto.ExerciseResponse;
import com.example.fitguru.program.dto.ProgramCreateRequest;
import com.example.fitguru.program.dto.ProgramDayCreateRequest;
import com.example.fitguru.program.dto.ProgramExerciseCreateRequest;
import com.example.fitguru.program.dto.ProgramWeekCreateRequest;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.program.dto.ProgramDayResponse;
import com.example.fitguru.program.dto.ProgramExerciseResponse;
import com.example.fitguru.program.dto.ProgramWeekResponse;

import java.util.List;

import retrofit2.Callback;

public class ProgramCreateRepository {

    private final ApiService api;

    public ProgramCreateRepository(ApiService api) {
        this.api = api;
    }

    public void createProgram(
            ProgramCreateRequest request,
            Callback<ProgramResponse> callback
    ) {
        api.createProgram(request).enqueue(callback);
    }

    public void createWeek(
            ProgramWeekCreateRequest request,
            Callback<ProgramWeekResponse> callback
    ) {
        api.createWeek(request).enqueue(callback);
    }

    public void createDay(
            List<ProgramDayCreateRequest> requests,
            Callback<Void> callback
    ) {
        api.createDay(requests).enqueue(callback);
    }

    public void createExercise(
            ProgramExerciseCreateRequest request,
            Callback<ProgramExerciseResponse> callback
    ) {
        api.createExercise(request).enqueue(callback);
    }

    public void getExercisesByGroup(
            String muscleGroup,
            Callback<List<ExerciseResponse>> callback
    ) {
        api.getExercisesByGroup(muscleGroup)
                .enqueue(callback);
    }
}
