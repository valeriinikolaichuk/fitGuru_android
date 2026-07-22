package com.fitguru.backend.trainer.controller;

import com.fitguru.backend.trainer.dto.ClientResponse;
import com.fitguru.backend.trainer.service.TrainerService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @Test
    void clients_shouldReturnClientList() throws Exception {

        String token = "Bearer jwt-token";

        ClientResponse response = new ClientResponse(
                1L,
                10L,
                "John Client",
                "380991112233"
        );

        when(trainerService.getClients(token))
                .thenReturn(List.of(response));

        mockMvc.perform(
                get("/trainer/clients")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].trainerClientId").value(10))
        .andExpect(jsonPath("$[0].name").value("John Client"))
        .andExpect(jsonPath("$[0].phone").value("380991112233"));

        verify(trainerService).getClients(token);
    }
}
