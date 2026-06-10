package com.fitguru.backend.program.dto;

import com.fitguru.backend.program.entity.enums.TrainingDay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDayResponse {

    private Long id;

    private TrainingDay day;

    private Integer position;
}
