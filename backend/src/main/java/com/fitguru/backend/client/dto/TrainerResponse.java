package com.fitguru.backend.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponse {
    private Long id;
    private Long trainerClientId;
    private String name;
    private String phone;
}
