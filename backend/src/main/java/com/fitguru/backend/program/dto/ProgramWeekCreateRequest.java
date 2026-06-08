package com.fitguru.backend.program.dto;

import lombok.Data;

@Data
public class ProgramWeekCreateRequest {

    private Long programId;

    private String title;

    private Integer position;
}
