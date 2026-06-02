package com.fitguru.backend.program.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProgramWeekDto {

    private String title;

    private Integer position;

    private List<ProgramDayDto> days;
}
