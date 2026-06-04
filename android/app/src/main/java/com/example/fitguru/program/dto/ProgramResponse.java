package com.example.fitguru.program.dto;

import com.example.fitguru.program.model.enums.ProgramStatus;

public class ProgramResponse {

    private Long id;

    private String title;

    private ProgramStatus status;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ProgramStatus getStatus() {
        return status;
    }
}
