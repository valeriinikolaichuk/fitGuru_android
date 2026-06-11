package com.example.fitguru.program.dto;

public class ProgramExerciseResponse {

    public Long id;

    public Long exerciseId;

    public String exerciseName;

    public Integer position;

    public Double weight;

    public Integer sets;

    public Integer reps;

    public String notes;

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

    public Double getWeight() {
        return weight;
    }

    public Integer getSets() {
        return sets;
    }

    public Integer getReps() {
        return reps;
    }

    public String getNotes() {
        return notes;
    }
}
