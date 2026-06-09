package com.fitguru.backend.program.service;

import org.springframework.stereotype.Service;

import com.fitguru.backend.program.dto.ProgramWeekCreateRequest;
import com.fitguru.backend.program.dto.ProgramWeekResponse;
import com.fitguru.backend.program.entity.Program;
import com.fitguru.backend.program.entity.ProgramWeek;
import com.fitguru.backend.program.mapper.ProgramWeekMapper;
import com.fitguru.backend.program.repository.ProgramRepository;
import com.fitguru.backend.program.repository.ProgramWeekRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramWeekService {

    private final ProgramRepository programRepository;
    private final ProgramWeekRepository weekRepository;

    public ProgramWeekResponse create(
            ProgramWeekCreateRequest request
    ) {

        Program program = programRepository
                .findById(request.getProgramId())
                .orElseThrow();

        ProgramWeek week = ProgramWeek.builder()
                .program(program)
                .title(request.getTitle())
                .position(request.getPosition())
                .build();

        week = weekRepository.save(week);

        return ProgramWeekResponse.builder()
                .id(week.getId())
                .title(week.getTitle())
                .position(week.getPosition())
                .build();
    }

    public List<ProgramWeekResponse> getByProgram(Long programId) {

    return weekRepository
            .findByProgramIdOrderByPosition(programId)
            .stream()
            .map(ProgramWeekMapper::toResponse)
            .toList();
}
}