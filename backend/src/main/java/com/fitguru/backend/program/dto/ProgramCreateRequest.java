package com.fitguru.backend.program.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProgramCreateRequest {

    private Long trainerClientId;

    private String title;

    private List<ProgramWeekDto> weeks;
}
