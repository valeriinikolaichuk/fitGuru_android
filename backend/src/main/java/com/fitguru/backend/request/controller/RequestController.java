package com.fitguru.backend.request.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fitguru.backend.request.service.RequestService;;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    
    private final RequestService requestService;

    @PostMapping("/{trainerId}")
    public ResponseEntity<Void> sendRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long trainerId
    ) {
        requestService.sendRequest(token, trainerId);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{trainerId}")
    public ResponseEntity<Void> cancelRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long trainerId
    ) {

        requestService.cancelRequest(token, trainerId);

        return ResponseEntity.ok().build();
    }
}

//POST /requests
//GET /trainer/requests
//POST /requests/{id}/accept
//POST /requests/{id}/reject