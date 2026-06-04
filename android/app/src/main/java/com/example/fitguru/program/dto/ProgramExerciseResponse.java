package com.example.fitguru.program.dto;

public class ProgramExerciseResponse {

    private Long id;

    public Long exerciseId;

    private String exerciseName;

    public Integer position;

    public Long getId() {
        return id;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public Integer getPosition() {
        return position;
    }
}
