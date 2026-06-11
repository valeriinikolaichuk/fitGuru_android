package com.example.fitguru.repository;

import com.example.fitguru.network.ApiService;
import com.example.fitguru.program.dto.ExerciseResponse;
import com.example.fitguru.program.dto.ProgramCreateRequest;
import com.example.fitguru.program.dto.ProgramDayCreateRequest;
import com.example.fitguru.program.dto.ProgramExerciseCreateRequest;
import com.example.fitguru.program.dto.ProgramUpdateRequest;
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

    public void updateProgram(
            Long id,
            ProgramUpdateRequest request,
            Callback<ProgramResponse> callback
    ) {
        api.updateProgram(id, request).enqueue(callback);
    }

    public void getProgram(
            Long id,
            Callback<ProgramResponse> callback
    ) {
        api.getProgram(id).enqueue(callback);
    }

    public void deleteProgram(
            Long programId,
            Callback<Void> callback
    ) {
        api.deleteProgram(programId).enqueue(callback);
    }

// -- WEEK

    public void createWeek(
            ProgramWeekCreateRequest request,
            Callback<ProgramWeekResponse> callback
    ) {
        api.createWeek(request).enqueue(callback);
    }

    public void getWeeksByProgram(
            Long programId,
            Callback<List<ProgramWeekResponse>> callback
    ) {
        api.getWeeksByProgram(programId).enqueue(callback);
    }

    public void deleteWeek(
            Long weekId,
            Callback<Void> callback
    ) {
        api.deleteWeek(weekId).enqueue(callback);
    }

// -- DAY

    public void createDay(
            List<ProgramDayCreateRequest> requests,
            Callback<Void> callback
    ) {
        api.createDay(requests).enqueue(callback);
    }

    public void getDaysByWeek(
            Long weekId,
            Callback<List<ProgramDayResponse>> callback
    ) {
        api.getDaysByWeek(weekId).enqueue(callback);
    }

    public void deleteDay(
            Long dayId,
            Callback<Void> callback
    ) {
        api.deleteWeek(dayId).enqueue(callback);
    }

// -- EXERCISE

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
        api.getExercisesByGroup(muscleGroup).enqueue(callback);
    }

    public void getExercisesByDay(
            Long dayId,
            Callback<List<ProgramExerciseResponse>> callback
    ) {
        api.getExercisesByDay(dayId).enqueue(callback);
    }
}
