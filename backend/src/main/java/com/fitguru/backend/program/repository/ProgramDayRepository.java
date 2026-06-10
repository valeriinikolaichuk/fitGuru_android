package com.fitguru.backend.program.repository;

import com.fitguru.backend.program.entity.ProgramDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramDayRepository
        extends JpaRepository<ProgramDay, Long> {
    List<ProgramDay> findByWeekIdOrderByPosition(Long weekId);
}
