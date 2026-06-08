package com.fitguru.backend.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private Long trainerClientId;
    private String name;
    private String phone;
}
