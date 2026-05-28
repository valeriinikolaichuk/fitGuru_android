package com.fitguru.backend.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingRequestResponse {

    private Long requestId;

    private Long clientId;

    private String clientName;

    private String clientPhone;
}
