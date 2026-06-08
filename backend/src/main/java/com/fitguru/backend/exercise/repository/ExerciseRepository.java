package com.fitguru.backend.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitguru.backend.exercise.entity.Exercise;

public interface ExerciseRepository
        extends JpaRepository<Exercise, Long> {
}