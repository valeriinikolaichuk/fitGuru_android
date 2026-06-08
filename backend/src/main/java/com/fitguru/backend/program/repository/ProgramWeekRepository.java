package com.fitguru.backend.program.repository;

import com.fitguru.backend.program.entity.ProgramWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramWeekRepository
        extends JpaRepository<ProgramWeek, Long> {
}
