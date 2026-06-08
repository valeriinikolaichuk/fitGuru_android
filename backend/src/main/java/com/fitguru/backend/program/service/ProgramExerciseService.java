package com.fitguru.backend.program.service;

import org.springframework.stereotype.Service;

import com.fitguru.backend.program.repository.*;
import com.fitguru.backend.program.dto.*;
import com.fitguru.backend.exercise.repository.ExerciseRepository;
import com.fitguru.backend.program.entity.ProgramDay;
import com.fitguru.backend.program.entity.ProgramExercise;
import com.fitguru.backend.exercise.entity.Exercise;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramExerciseService {

    private final ProgramDayRepository dayRepository;
    private final ExerciseRepository exerciseRepository;
    private final ProgramExerciseRepository exerciseProgramRepository;

    public ProgramExerciseResponse create(
            ProgramExerciseCreateRequest request
    ) {

        ProgramDay day = dayRepository
                .findById(request.getDayId())
                .orElseThrow();

        Exercise exercise = exerciseRepository
                .findById(request.getExerciseId())
                .orElseThrow();

        ProgramExercise programExercise = ProgramExercise.builder()
                .day(day)
                .exercise(exercise)
                .position(request.getPosition())
                .weight(request.getWeight())
                .sets(request.getSets())
                .reps(request.getReps())
                .notes(request.getNotes())
                .build();

        programExercise =
                exerciseProgramRepository.save(programExercise);

        return ProgramExerciseResponse.builder()
                .id(programExercise.getId())
                .exerciseId(exercise.getId())
                .exerciseName(exercise.getExercise())
                .position(programExercise.getPosition())
                .build();
    }
}