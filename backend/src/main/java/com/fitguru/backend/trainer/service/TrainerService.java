package com.fitguru.backend.trainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.trainer.dto.ClientResponse;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TrainerService {
    
    private final TrainerClientRepository repository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public List<ClientResponse> getClients(String token) {

        String phone = jwtService.extractPhone(token.replace("Bearer ", ""));

        User trainer = userRepository.findByPhone(phone)
                .orElseThrow();

        return repository.findByTrainer(trainer)
                .stream()
                .map(tc -> new ClientResponse(
                        tc.getClient().getId(),
                        tc.getClient().getName(),
                        tc.getClient().getPhone()
                ))
                .collect(Collectors.toList());
    }
}
