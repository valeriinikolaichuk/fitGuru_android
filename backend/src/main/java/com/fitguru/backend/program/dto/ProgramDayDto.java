package com.fitguru.backend.program.dto;

import java.util.List;
import lombok.Data;

import com.fitguru.backend.program.entity.enums.TrainingDay;

@Data
public class ProgramDayDto {

    private TrainingDay day;

    private Integer position;

    private List<ProgramExerciseDto> exercises;
}
