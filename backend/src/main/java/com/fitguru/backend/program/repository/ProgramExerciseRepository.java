package com.fitguru.backend.program.repository;

import com.fitguru.backend.program.entity.ProgramExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramExerciseRepository
        extends JpaRepository<ProgramExercise, Long> {
}
