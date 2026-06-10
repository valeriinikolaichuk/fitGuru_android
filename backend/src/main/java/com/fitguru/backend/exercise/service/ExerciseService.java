package com.fitguru.backend.exercise.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.fitguru.backend.exercise.repository.ExerciseRepository;
import com.fitguru.backend.exercise.dto.ExerciseResponse;
import com.fitguru.backend.exercise.entity.enums.MuscleGroup;
import com.fitguru.backend.exercise.entity.Exercise;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseResponse> getByMuscleGroup(String muscleGroup) {

        List<Exercise> exercises;

        if (muscleGroup == null || muscleGroup.isEmpty()) {
            exercises = exerciseRepository.findAll();
        } else {
            exercises = exerciseRepository.findByMuscleGroup(
                    MuscleGroup.valueOf(muscleGroup)
            );
        }

        return exercises.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExerciseResponse mapToResponse(Exercise e) {
        ExerciseResponse dto = new ExerciseResponse();
        dto.id = e.getId();
        dto.exercise = e.getExercise();
        dto.muscleGroup = e.getMuscleGroup().name();
        return dto;
    }
}
