package com.fitguru.backend.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.fitguru.backend.client.dto.TrainerResponse;
import com.fitguru.backend.client.service.ClientService;

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
}


//POST /client/request-trainer
//GET /client/programs