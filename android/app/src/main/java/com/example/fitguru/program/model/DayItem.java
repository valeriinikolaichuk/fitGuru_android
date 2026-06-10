package com.example.fitguru.program.model;

import com.example.fitguru.program.model.enums.TrainingDay;

public class DayItem {
    private Long id;
    private TrainingDay day;
    private int position;

    public DayItem(Long id, TrainingDay day, int position) {
        this.id = id;
        this.day = day;
        this.position = position;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TrainingDay getDay() { return day; }

    public int getPosition() { return position; }

    public void setPosition(int position) {
        this.position = position;
    }
}
