package com.fitguru.backend.request.service;

import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.repository.UserRepository;
import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.request.repository.TrainingRequestRepository;
import com.fitguru.backend.trainer.entity.TrainerClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitguru.backend.request.dto.TrainingRequestResponse;
import com.fitguru.backend.request.entity.TrainingRequest;
import com.fitguru.backend.request.entity.enums.RequestStatus;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private TrainingRequestRepository trainingRequestRepository;

    @Mock
    private TrainerClientRepository trainerClientRepository;

    @InjectMocks
    private RequestService requestService;

    @Test
    void sendRequest_shouldSaveTrainingRequest() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .phone("380991112233")
                .build();

        User trainer = User.builder()
                .id(2L)
                .phone("380671112233")
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(trainer));

        when(trainingRequestRepository.existsByClientAndTrainerAndStatus(
                client,
                trainer,
                RequestStatus.PENDING))
                .thenReturn(false);

        requestService.sendRequest(token, 2L);

        verify(trainingRequestRepository).save(any(TrainingRequest.class));
    }

    @Test
    void sendRequest_shouldNotSaveDuplicateRequest() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .phone("380991112233")
                .build();

        User trainer = User.builder()
                .id(2L)
                .phone("380671112233")
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(trainer));

        when(trainingRequestRepository.existsByClientAndTrainerAndStatus(
                client,
                trainer,
                RequestStatus.PENDING))
                .thenReturn(true);

        requestService.sendRequest(token, 2L);

        verify(trainingRequestRepository, never())
                .save(any(TrainingRequest.class));
    }

    @Test
    void cancelRequest_shouldDeletePendingRequest() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .phone("380991112233")
                .build();

        User trainer = User.builder()
                .id(2L)
                .phone("380671112233")
                .build();

        TrainingRequest request = TrainingRequest.builder()
                .id(100L)
                .client(client)
                .trainer(trainer)
                .status(RequestStatus.PENDING)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(trainer));

        when(trainingRequestRepository.findByClientAndTrainerAndStatus(
                client,
                trainer,
                RequestStatus.PENDING))
                .thenReturn(Optional.of(request));

        requestService.cancelRequest(token, 2L);

        verify(trainingRequestRepository).delete(request);
    }

    @Test
    void getRequests_shouldReturnTrainingRequests() {

        String token = "Bearer jwt-token";

        User trainer = User.builder()
                .id(1L)
                .phone("380991112233")
                .build();

        User client = User.builder()
                .id(2L)
                .name("John Client")
                .phone("380671112233")
                .build();

        TrainingRequest request = TrainingRequest.builder()
                .id(100L)
                .trainer(trainer)
                .client(client)
                .status(RequestStatus.PENDING)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(trainer));

        when(trainingRequestRepository.findByTrainerAndStatus(
                trainer,
                RequestStatus.PENDING))
                .thenReturn(List.of(request));

        List<TrainingRequestResponse> result = requestService.getRequests(token);

        assertNotNull(result);
        assertEquals(1, result.size());

        TrainingRequestResponse response = result.get(0);

        assertEquals(100L, response.getRequestId());
        assertEquals(2L, response.getClientId());
        assertEquals("John Client", response.getClientName());
        assertEquals("380671112233", response.getClientPhone());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(trainingRequestRepository)
                .findByTrainerAndStatus(trainer, RequestStatus.PENDING);
    }

    @Test
    void getRequests_shouldReturnEmptyList() {

        String token = "Bearer jwt-token";

        User trainer = User.builder()
                .id(1L)
                .phone("380991112233")
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(trainer));

        when(trainingRequestRepository.findByTrainerAndStatus(
                trainer,
                RequestStatus.PENDING))
                .thenReturn(List.of());

        List<TrainingRequestResponse> result = requestService.getRequests(token);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(trainingRequestRepository)
                .findByTrainerAndStatus(trainer, RequestStatus.PENDING);
    }

    @Test
    void acceptRequest_shouldAcceptTrainingRequest() {

        User trainer = User.builder()
                .id(1L)
                .build();

        User client = User.builder()
                .id(2L)
                .build();

        TrainingRequest request = TrainingRequest.builder()
                .id(100L)
                .trainer(trainer)
                .client(client)
                .status(RequestStatus.PENDING)
                .build();

        when(trainingRequestRepository.findById(100L))
                .thenReturn(Optional.of(request));

        requestService.acceptRequest(100L);

        assertEquals(RequestStatus.ACCEPTED, request.getStatus());

        verify(trainerClientRepository)
                .save(any(TrainerClient.class));

        verify(trainingRequestRepository)
                .save(request);
    }

    @Test
    void rejectRequest_shouldRejectTrainingRequest() {

        User trainer = User.builder()
                .id(1L)
                .build();

        User client = User.builder()
                .id(2L)
                .build();

        TrainingRequest request = TrainingRequest.builder()
                .id(100L)
                .trainer(trainer)
                .client(client)
                .status(RequestStatus.PENDING)
                .build();

        when(trainingRequestRepository.findById(100L))
                .thenReturn(Optional.of(request));

        requestService.rejectRequest(100L);

        assertEquals(RequestStatus.REJECTED, request.getStatus());

        verify(trainingRequestRepository)
                .save(request);

        verifyNoInteractions(trainerClientRepository);
    }
}
