package com.fitguru.backend.program.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitguru.backend.program.dto.ProgramDayCreateRequest;
import com.fitguru.backend.program.entity.ProgramDay;
import com.fitguru.backend.program.entity.ProgramWeek;
import com.fitguru.backend.program.repository.ProgramDayRepository;
import com.fitguru.backend.program.repository.ProgramWeekRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramDayService {

    private final ProgramDayRepository dayRepository;
    private final ProgramWeekRepository weekRepository;

    public void create(List<ProgramDayCreateRequest> requests) {

        for (ProgramDayCreateRequest request : requests) {

            ProgramWeek week = weekRepository
                    .findById(request.getWeekId())
                    .orElseThrow();

            ProgramDay day = ProgramDay.builder()
                    .week(week)
                    .day(request.getDay())
                    .position(request.getPosition())
                    .build();

            dayRepository.save(day);
        }
    }
}