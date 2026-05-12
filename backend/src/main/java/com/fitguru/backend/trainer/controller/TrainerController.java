package com.fitguru.backend.trainer.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.fitguru.backend.trainer.dto.ClientResponse;
import com.fitguru.backend.trainer.service.TrainerService;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerController {
    
    private final TrainerService trainerService;

    @GetMapping("/clients")
    public List<ClientResponse> clients(
            @RequestHeader("Authorization") String token
    ) {
        return trainerService.getClients(token);
    }
}
