package com.fitguru.backend.program.mapper;

import org.springframework.stereotype.Component;

import com.fitguru.backend.program.dto.ProgramResponse;
import com.fitguru.backend.program.entity.Program;

@Component
public class ProgramMapper {

    public ProgramResponse toResponse(Program program) {

        ProgramResponse response = new ProgramResponse();

        response.setId(program.getId());
        response.setTitle(program.getTitle());

        response.setTrainerClientId(
                program.getTrainerClient().getId()
        );

        return response;
    }
}
