package com.fitguru.backend.program.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramWeekResponse {

    private Long id;

    private String title;

    private Integer position;
}
