package com.fitguru.backend.program.dto;

import lombok.Data;

@Data
public class ProgramCreateRequest {

    private Long trainerClientId;

    private String title;
}
