package com.fitguru.backend.exercise.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.fitguru.backend.exercise.service.ExerciseService;
import com.fitguru.backend.exercise.dto.ExerciseResponse;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseResponse> getByMuscleGroup(
            @RequestParam(required = false) String muscleGroup
    ) {
        return exerciseService.getByMuscleGroup(muscleGroup);
    }
}
