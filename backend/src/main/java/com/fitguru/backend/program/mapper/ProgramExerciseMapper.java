package com.fitguru.backend.program.mapper;

import com.fitguru.backend.program.dto.ProgramExerciseResponse;
import com.fitguru.backend.program.entity.ProgramExercise;

public class ProgramExerciseMapper {

    public static ProgramExerciseResponse toResponse(
            ProgramExercise exercise
    ) {
        return ProgramExerciseResponse.builder()
                .id(exercise.getId())
                .exerciseId(exercise.getExercise().getId())
                .exerciseName(exercise.getExercise().getExercise())
                .position(exercise.getPosition())
                .weight(exercise.getWeight())
                .sets(exercise.getSets())
                .reps(exercise.getReps())
                .notes(exercise.getNotes())
                .build();
    }
}
