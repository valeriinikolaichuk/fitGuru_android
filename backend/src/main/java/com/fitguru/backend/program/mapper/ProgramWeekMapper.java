package com.fitguru.backend.program.mapper;

import com.fitguru.backend.program.dto.ProgramWeekResponse;
import com.fitguru.backend.program.entity.ProgramWeek;

public class ProgramWeekMapper {

    public static ProgramWeekResponse toResponse(ProgramWeek week) {

        return ProgramWeekResponse.builder()
                .id(week.getId())
                .title(week.getTitle())
                .position(week.getPosition())
                .build();
    }
}
