package com.fitguru.backend.program.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramExerciseResponse {

    private Long id;

    private Long exerciseId;

    private String exerciseName;

    private Integer position;

    public Double weight;

    public Integer sets;

    public Integer reps;

    public String notes;
}
