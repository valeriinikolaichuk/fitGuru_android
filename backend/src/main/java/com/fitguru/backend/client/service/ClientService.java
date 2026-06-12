package com.fitguru.backend.client.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.entity.enums.Role;
import com.fitguru.backend.auth.repository.UserRepository;
import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.client.dto.AvailableTrainerResponse;
import com.fitguru.backend.client.dto.TrainerResponse;
import com.fitguru.backend.request.entity.TrainingRequest;
import com.fitguru.backend.request.repository.TrainingRequestRepository;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;

import lombok.RequiredArgsConstructor;
import java.util.Set;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final TrainerClientRepository trainerClientRepository;
    private final UserRepository userRepository;
    private final TrainingRequestRepository trainingRequestRepository;
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
                        tc.getId(), // TrainerClient ID
                        tc.getTrainer().getName(),
                        tc.getTrainer().getPhone()
                ))
                .toList();
    }

    public List<AvailableTrainerResponse> getAvailableTrainers(String token) {

        String phone = jwtService.extractPhone(
            token.replace("Bearer ", "")
        );

        User client = userRepository.findByPhone(phone)
                .orElseThrow();

        Set<Long> connectedTrainerIds = trainerClientRepository.findByClient(client)
                .stream()
                .map(tc -> tc.getTrainer().getId())
                .collect(Collectors.toSet());

        List<TrainingRequest> requests = trainingRequestRepository.findByClient(client);

        Map<Long, String> statuses = requests.stream()
            .collect(Collectors.toMap(
                    r -> r.getTrainer().getId(),
                    r -> r.getStatus().name()
            ));

        List<User> allTrainers = userRepository.findByRole(Role.TRAINER);

        return allTrainers.stream()
        .filter(tr -> !connectedTrainerIds.contains(tr.getId()))
        .map(tr -> new AvailableTrainerResponse(
                tr.getId(),
                tr.getName(),
                tr.getPhone(),
                statuses.getOrDefault(tr.getId(), "NONE")
        ))
        .toList();
    }
}
