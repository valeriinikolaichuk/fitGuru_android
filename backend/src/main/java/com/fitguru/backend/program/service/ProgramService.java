package com.fitguru.backend.program.service;

import org.springframework.stereotype.Service;

import com.fitguru.backend.program.dto.ProgramCreateRequest;
import com.fitguru.backend.program.dto.ProgramResponse;
import com.fitguru.backend.program.dto.ProgramUpdateRequest;
import com.fitguru.backend.program.entity.Program;
import com.fitguru.backend.program.mapper.ProgramMapper;
import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;
import com.fitguru.backend.program.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final TrainerClientRepository trainerClientRepository;
    private final ProgramMapper programMapper;

    public ProgramResponse create(ProgramCreateRequest request) {

        TrainerClient trainerClient =
                trainerClientRepository.findById(request.getTrainerClientId())
                        .orElseThrow();

        Program program = Program.builder()
                .trainerClient(trainerClient)
                .title(request.getTitle())
                .build();

        program = programRepository.save(program);

        return ProgramResponse.builder()
                .id(program.getId())
                .title(program.getTitle())
                .status(program.getStatus())
                .build();
    }

    public List<ProgramResponse> getProgramsByClient(Long clientId) {

        return programRepository.findByTrainerClientId(clientId)
                .stream()
                .map(programMapper::toResponse)
                .toList();
    }

    public ProgramResponse update(
        Long id,
        ProgramUpdateRequest request
    ) {

        Program program = programRepository
                .findById(id)
                .orElseThrow();

        program.setTitle(request.getTitle());

        program = programRepository.save(program);

        return ProgramResponse.builder()
                .id(program.getId())
                .title(program.getTitle())
                .status(program.getStatus())
                .build();
    }
}