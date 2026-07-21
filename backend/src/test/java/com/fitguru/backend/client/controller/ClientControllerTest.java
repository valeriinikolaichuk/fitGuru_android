package com.fitguru.backend.client.controller;

import com.fitguru.backend.client.dto.AvailableTrainerResponse;
import com.fitguru.backend.client.dto.TrainerResponse;
import com.fitguru.backend.client.service.ClientService;

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

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;


    @Test
    void trainers_shouldReturnTrainerList() throws Exception {

        String token = "Bearer jwt-token";

        TrainerResponse response =
                new TrainerResponse(
                        1L,
                        10L,
                        "John Trainer",
                        "380991112233"
                );


        when(clientService.getTrainers(token))
                .thenReturn(List.of(response));


        mockMvc.perform(
                get("/client/trainers")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].trainerClientId").value(10))
        .andExpect(jsonPath("$[0].name").value("John Trainer"))
        .andExpect(jsonPath("$[0].phone")
                .value("380991112233"));


        verify(clientService)
                .getTrainers(token);
    }


    @Test
    void getAvailableTrainers_shouldReturnAvailableTrainerList()
            throws Exception {

        String token = "Bearer jwt-token";


        AvailableTrainerResponse response =
                new AvailableTrainerResponse(
                        2L,
                        "Mike Trainer",
                        "380671112233",
                        "PENDING"
                );


        when(clientService.getAvailableTrainers(token))
                .thenReturn(List.of(response));


        mockMvc.perform(
                get("/client/trainers/available")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(2))
        .andExpect(jsonPath("$[0].name")
                .value("Mike Trainer"))
        .andExpect(jsonPath("$[0].requestStatus")
                .value("PENDING"));


        verify(clientService)
                .getAvailableTrainers(token);
    }
}
