package com.fitguru.backend.exercise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseResponse {
    
    public Long id;

    public String exercise;

    public String muscleGroup;

        public ExerciseResponse() {
    }

    public ExerciseResponse(Long id, String exercise, String muscleGroup) {
        this.id = id;
        this.exercise = exercise;
        this.muscleGroup = muscleGroup;
    }
}
