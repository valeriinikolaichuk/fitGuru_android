package com.fitguru.backend.program.repository;

import com.fitguru.backend.program.entity.ProgramWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramWeekRepository
        extends JpaRepository<ProgramWeek, Long> {
    List<ProgramWeek> findByProgramIdOrderByPosition(Long programId);
}
