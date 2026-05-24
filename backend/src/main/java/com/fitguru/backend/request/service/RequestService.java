package com.fitguru.backend.request.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.request.entity.TrainingRequest;
import com.fitguru.backend.request.entity.enums.RequestStatus;
import com.fitguru.backend.user.repository.UserRepository;
import com.fitguru.backend.request.repository.TrainingRequestRepository;

import lombok.RequiredArgsConstructor;

import com.fitguru.backend.user.entity.User;

@Service
@RequiredArgsConstructor
public class RequestService {
    
    private final UserRepository userRepository;
    private final TrainingRequestRepository trainingRequestRepository;
    private final JwtService jwtService;

    @Transactional
    public void sendRequest(String token, Long trainerId) {

        String phone = jwtService.extractPhone(
            token.replace("Bearer ", "")
        );

        User client = userRepository.findByPhone(phone)
                .orElseThrow();

        User trainer = userRepository.findById(trainerId)
                .orElseThrow();

        boolean exists = trainingRequestRepository
                .existsByClientAndTrainerAndStatus(
                        client,
                        trainer,
                        RequestStatus.PENDING
                );

        if (exists) return;

        TrainingRequest request = TrainingRequest.builder()
                .client(client)
                .trainer(trainer)
                .status(RequestStatus.PENDING)
                .build();

        trainingRequestRepository.save(request);
    }

    @Transactional
    public void cancelRequest(String token, Long trainerId) {

        String phone = jwtService.extractPhone(token.replace(
            "Bearer ", "")
        );

        User client = userRepository.findByPhone(phone)
                .orElseThrow();

        User trainer = userRepository.findById(trainerId)
                .orElseThrow();

        TrainingRequest request =
                trainingRequestRepository
                        .findByClientAndTrainerAndStatus(
                                client,
                                trainer,
                                RequestStatus.PENDING
                        )
                        .orElseThrow();

        trainingRequestRepository.delete(request);
    }
}
