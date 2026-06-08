package com.fitguru.backend.trainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.trainer.dto.ClientResponse;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role;
import com.fitguru.backend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TrainerService {
    
    private final TrainerClientRepository repository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public List<ClientResponse> getClients(String token) {

        String phone = jwtService.extractPhone(
            token.replace("Bearer ", "")
        );

        User trainer = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (trainer.getRole() != Role.TRAINER) {
            throw new RuntimeException("Access denied");
        }

        return repository.findByTrainer(trainer)
        .stream()
        .map(tc -> new ClientResponse(
                tc.getClient().getId(),
                tc.getId(),               // TrainerClient ID
                tc.getClient().getName(),
                tc.getClient().getPhone()
        ))
        .toList();
    }
}
