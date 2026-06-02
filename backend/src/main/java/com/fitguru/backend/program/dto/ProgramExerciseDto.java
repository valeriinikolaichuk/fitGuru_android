package com.fitguru.backend.program.dto;

import lombok.Data;

@Data
public class ProgramExerciseDto {

    private Long exerciseId;

    private Integer position;

    private Double weight;

    private Integer sets;

    private Integer reps;

    private String notes;
}
