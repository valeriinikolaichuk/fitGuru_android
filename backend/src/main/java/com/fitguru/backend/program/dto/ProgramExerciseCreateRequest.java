package com.fitguru.backend.program.dto;

import lombok.Data;

@Data
public class ProgramExerciseCreateRequest {

    private Long exerciseId;
    private Long dayId;

    private Integer position;

    private Double weight;
    private Integer sets;
    private Integer reps;

    private String notes;
}
