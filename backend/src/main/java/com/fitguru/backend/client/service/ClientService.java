package com.fitguru.backend.client.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.client.dto.TrainerResponse;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role;
import com.fitguru.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final TrainerClientRepository trainerClientRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public List<TrainerResponse> getTrainers(String token) {

        String phone = jwtService.extractPhone(
            token.replace("Bearer ", "")
        );

        User client = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (client.getRole() != Role.CLIENT) {
            throw new RuntimeException("Access denied");
        }

        return trainerClientRepository.findByClient(client)
                .stream()
                .map(tc -> new TrainerResponse(
                        tc.getTrainer().getId(),
                        tc.getTrainer().getName(),
                        tc.getTrainer().getPhone()
                ))
                .toList();
    }
}
