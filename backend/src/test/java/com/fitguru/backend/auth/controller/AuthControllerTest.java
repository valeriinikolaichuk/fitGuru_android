package com.fitguru.backend.auth.controller;

import com.fitguru.backend.auth.dto.LoginRequest;
import com.fitguru.backend.auth.dto.LoginResponse;
import com.fitguru.backend.auth.dto.RegisterRequest;
import com.fitguru.backend.auth.service.AuthService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void register_shouldReturnLoginResponse() throws Exception {

        RegisterRequest request = new RegisterRequest();

        request.setName("John");
        request.setPhone("380991112233");
        request.setPassword("password");
        request.setRole("CLIENT");

        LoginResponse response =
                new LoginResponse(
                        "access-token",
                        "refresh-token",
                        "CLIENT"
                );

        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.accessToken").value("access-token")
        )
        .andExpect(
                jsonPath("$.refreshToken")
                        .value("refresh-token")
        )
        .andExpect(
                jsonPath("$.role")
                        .value("CLIENT")
        );

        verify(authService).register(any(RegisterRequest.class));
    }

    @Test
    void login_shouldReturnLoginResponse() throws Exception {

        LoginRequest request = new LoginRequest();

        request.setPhone("380991112233");
        request.setPassword("password");

        LoginResponse response =
                new LoginResponse(
                        "access-token",
                        "refresh-token",
                        "CLIENT"
                );

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.accessToken")
                        .value("access-token")
        );

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void refresh_shouldReturnLoginResponse() throws Exception {

        LoginResponse response =
                new LoginResponse(
                        "new-access",
                        "new-refresh",
                        "CLIENT"
                );

        when(authService.refresh(any())).thenReturn(response);

        mockMvc.perform(
                post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "refreshToken": "old-refresh-token"
                            }
                        """)
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.accessToken")
                        .value("new-access")
        );

        verify(authService).refresh(any());
    }
}
