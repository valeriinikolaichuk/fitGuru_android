package com.fitguru.backend.program.dto;

import com.fitguru.backend.program.entity.enums.TrainingDay;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramDayResponse {

    private Long id;

    private TrainingDay day;
    
    private Integer position;
}
