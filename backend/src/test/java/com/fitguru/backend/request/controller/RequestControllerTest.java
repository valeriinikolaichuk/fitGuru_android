package com.fitguru.backend.request.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fitguru.backend.request.dto.TrainingRequestResponse;
import com.fitguru.backend.request.service.RequestService;

@WebMvcTest(RequestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RequestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @Test
    void sendRequest_shouldReturnOk() throws Exception {

        String token = "Bearer jwt-token";

        mockMvc.perform(
                post("/requests/1")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk());

        verify(requestService).sendRequest(token, 1L);
    }

    @Test
    void cancelRequest_shouldReturnOk() throws Exception {

        String token = "Bearer jwt-token";

        mockMvc.perform(
                delete("/requests/1")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk());

        verify(requestService).cancelRequest(token, 1L);
    }

    @Test
    void getRequests_shouldReturnRequestList() throws Exception {

        String token = "Bearer jwt-token";

        TrainingRequestResponse response =
                new TrainingRequestResponse(
                        10L,
                        2L,
                        "John Client",
                        "380671112233"
                );

        when(requestService.getRequests(token))
                .thenReturn(List.of(response));

        mockMvc.perform(
                get("/requests/trainer")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].requestId").value(10))
        .andExpect(jsonPath("$[0].clientId").value(2))
        .andExpect(jsonPath("$[0].clientName").value("John Client"))
        .andExpect(jsonPath("$[0].clientPhone").value("380671112233"));

        verify(requestService).getRequests(token);
    }

    @Test
    void acceptRequest_shouldReturnOk() throws Exception {

        String token = "Bearer jwt-token";

        mockMvc.perform(
                post("/requests/1/accept")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk());

        verify(requestService).acceptRequest(1L);
    }

    @Test
    void rejectRequest_shouldReturnOk() throws Exception {

        String token = "Bearer jwt-token";

        mockMvc.perform(
                post("/requests/1/reject")
                        .header("Authorization", token)
        )
        .andExpect(status().isOk());

        verify(requestService).rejectRequest(1L);
    }
}
