package com.fitguru.backend.program.repository;

import com.fitguru.backend.program.entity.ProgramDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramDayRepository
        extends JpaRepository<ProgramDay, Long> {
}
