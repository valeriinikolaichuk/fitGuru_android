package com.fitguru.backend.exercise.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fitguru.backend.exercise.entity.Exercise;
import com.fitguru.backend.exercise.entity.enums.MuscleGroup;

public interface ExerciseRepository
        extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);
}