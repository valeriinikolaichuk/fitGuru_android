package com.fitguru.backend.program.dto;

import lombok.Data;

import com.fitguru.backend.program.entity.enums.TrainingDay;

@Data
public class ProgramDayCreateRequest {

    private Long weekId;

    private TrainingDay day;

    private Integer position;
}
