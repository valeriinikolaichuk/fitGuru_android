package com.fitguru.backend.program.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fitguru.backend.program.dto.ProgramDayCreateRequest;
import com.fitguru.backend.program.dto.ProgramDayResponse;
import com.fitguru.backend.program.entity.ProgramDay;
import com.fitguru.backend.program.entity.ProgramWeek;
import com.fitguru.backend.program.repository.ProgramDayRepository;
import com.fitguru.backend.program.repository.ProgramWeekRepository;
import com.fitguru.backend.program.mapper.ProgramDayMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramDayService {

    private final ProgramDayRepository dayRepository;
    private final ProgramWeekRepository weekRepository;

    public List<ProgramDayResponse> create(
            List<ProgramDayCreateRequest> requests
    ) {

        List<ProgramDayResponse> result = new ArrayList<>();

        for (ProgramDayCreateRequest request : requests) {

            ProgramWeek week = weekRepository
                    .findById(request.getWeekId())
                    .orElseThrow();

            ProgramDay day = ProgramDay.builder()
                    .week(week)
                    .day(request.getDay())
                    .position(request.getPosition())
                    .build();

            day = dayRepository.save(day);

            result.add(
                    ProgramDayMapper.toResponse(day)
            );
        }

        return result;
    }

    public List<ProgramDayResponse> getByWeek(
            Long weekId
    ) {

        return dayRepository
                .findByWeekIdOrderByPosition(weekId)
                .stream()
                .map(ProgramDayMapper::toResponse)
                .toList();
    }
}