package com.fitguru.backend.request.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.request.dto.TrainingRequestResponse;
import com.fitguru.backend.request.entity.TrainingRequest;
import com.fitguru.backend.request.entity.enums.RequestStatus;
import com.fitguru.backend.user.repository.UserRepository;
import com.fitguru.backend.request.repository.TrainingRequestRepository;
import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

import com.fitguru.backend.user.entity.User;

@Service
@RequiredArgsConstructor
public class RequestService {
    
    private final UserRepository userRepository;
    private final TrainingRequestRepository trainingRequestRepository;
    private final TrainerClientRepository trainerClientRepository;
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

    public List<TrainingRequestResponse> getRequests(String token) {

        String phone = jwtService.extractPhone(
                token.replace("Bearer ", "")
        );

        User trainer = userRepository.findByPhone(phone)
                .orElseThrow();

        return trainingRequestRepository
                .findByTrainerAndStatus(
                        trainer,
                        RequestStatus.PENDING
                )
                .stream()
                .map(request -> new TrainingRequestResponse(
                        request.getId(),
                        request.getClient().getId(),
                        request.getClient().getName(),
                        request.getClient().getPhone()
                ))
                .toList();
    }

    @Transactional
    public void acceptRequest(Long requestId) {
 try {
        TrainingRequest request = trainingRequestRepository
                .findById(requestId)
                .orElseThrow();

        request.setStatus(RequestStatus.ACCEPTED);

        TrainerClient trainerClient = TrainerClient.builder()
                .trainer(request.getTrainer())
                .client(request.getClient())
                .build();

        trainerClientRepository.save(trainerClient);

        trainingRequestRepository.save(request);
        } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }
    }
}
