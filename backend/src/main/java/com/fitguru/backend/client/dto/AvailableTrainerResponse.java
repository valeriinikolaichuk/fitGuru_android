package com.fitguru.backend.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableTrainerResponse {

    private Long id;
    private String name;
    private String phone;

    private String requestStatus;
}
