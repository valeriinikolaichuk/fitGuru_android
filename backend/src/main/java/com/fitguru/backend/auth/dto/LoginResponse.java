package com.fitguru.backend.auth.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
