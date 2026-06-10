package com.fitguru.backend.program.mapper;

import com.fitguru.backend.program.dto.ProgramDayResponse;
import com.fitguru.backend.program.entity.ProgramDay;

public class ProgramDayMapper {
    
    public static ProgramDayResponse toResponse(
            ProgramDay day
    ) {
        return ProgramDayResponse.builder()
                .id(day.getId())
                .day(day.getDay())
                .position(day.getPosition())
                .build();
    }
}
