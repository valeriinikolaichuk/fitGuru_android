package com.fitguru.backend.program.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fitguru.backend.program.entity.enums.ProgramStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramResponse {

    private Long id;

    private String title;

    private ProgramStatus status;

    private Long trainerClientId;
}
