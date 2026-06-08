package com.fitguru.backend.program.repository;

import com.fitguru.backend.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository
    extends JpaRepository<Program, Long> {
        List<Program> findByTrainerClientId(Long trainerClientId);
}
