package com.fitguru.backend.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitguru.backend.auth.dto.LoginRequest;
import com.fitguru.backend.auth.dto.LoginResponse;
import com.fitguru.backend.auth.dto.RegisterRequest;
import com.fitguru.backend.auth.service.AuthService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturn200() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("John");
        request.setPhone("123456789");
        request.setPassword("password");

        doNothing().when(authService).register(request);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void login_ShouldReturnToken() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setPhone("123456789");
        request.setPassword("password");

        when(authService.login(request))
                .thenReturn(new LoginResponse("jwt-token"));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }
}
