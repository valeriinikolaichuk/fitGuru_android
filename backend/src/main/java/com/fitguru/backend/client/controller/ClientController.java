package com.fitguru.backend.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.fitguru.backend.client.dto.AvailableTrainerResponse;
import com.fitguru.backend.client.dto.TrainerResponse;
import com.fitguru.backend.client.service.ClientService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/trainers")
    public List<TrainerResponse> trainers(
        @RequestHeader("Authorization") String token
    ) {
        return clientService.getTrainers(token);
    }

    @GetMapping("/trainers/available")
    public List<AvailableTrainerResponse> getAvailableTrainers(
        HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        
        return clientService.getAvailableTrainers(token);
    }
}


//POST /client/request-trainer
//GET /client/programs