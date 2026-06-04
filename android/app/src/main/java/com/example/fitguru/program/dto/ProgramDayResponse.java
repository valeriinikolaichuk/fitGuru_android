package com.example.fitguru.program.dto;

import com.example.fitguru.program.model.enums.TrainingDay;

public class ProgramDayResponse {

    private Long id;

    private TrainingDay day;

    private Integer position;

    public Long getId() {
        return id;
    }

    public TrainingDay getDay() {
        return day;
    }

    public Integer getPosition() {
        return position;
    }
}
