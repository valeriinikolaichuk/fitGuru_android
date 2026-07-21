package com.fitguru.backend.client.service;

import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.entity.enums.Role;
import com.fitguru.backend.auth.repository.UserRepository;
import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.client.dto.AvailableTrainerResponse;
import com.fitguru.backend.client.dto.TrainerResponse;
import com.fitguru.backend.request.entity.TrainingRequest;
import com.fitguru.backend.request.entity.enums.RequestStatus;
import com.fitguru.backend.request.repository.TrainingRequestRepository;
import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.trainer.repository.TrainerClientRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private TrainerClientRepository trainerClientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRequestRepository trainingRequestRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ClientService clientService;

    @Test
    void getTrainers_shouldReturnTrainerList() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .name("Client")
                .phone("380991112233")
                .role(Role.CLIENT)
                .build();

        User trainer = User.builder()
                .id(2L)
                .name("Trainer")
                .phone("380671112233")
                .role(Role.TRAINER)
                .build();

        TrainerClient trainerClient = TrainerClient.builder()
                .id(10L)
                .client(client)
                .trainer(trainer)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(trainerClientRepository.findByClient(client))
                .thenReturn(List.of(trainerClient));

        List<TrainerResponse> result = clientService.getTrainers(token);

        assertNotNull(result);
        assertEquals(1, result.size());

        TrainerResponse response = result.get(0);

        assertEquals(2L, response.getId());
        assertEquals(10L, response.getTrainerClientId());
        assertEquals("Trainer", response.getName());
        assertEquals("380671112233", response.getPhone());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(trainerClientRepository).findByClient(client);
    }

    @Test
    void getTrainers_shouldThrowWhenUserNotFound() {

        String token = "Bearer jwt-token";

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> clientService.getTrainers(token)
        );

        assertEquals("User not found", exception.getMessage());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verifyNoInteractions(trainerClientRepository);
    }

    @Test
    void getTrainers_shouldThrowWhenUserIsNotClient() {

        String token = "Bearer jwt-token";

        User trainer = User.builder()
                .id(1L)
                .name("Trainer")
                .phone("380991112233")
                .role(Role.TRAINER)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(trainer));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> clientService.getTrainers(token)
        );

        assertEquals("Access denied", exception.getMessage());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");

        verifyNoInteractions(trainerClientRepository);
        verifyNoInteractions(trainingRequestRepository);
    }

    @Test
    void getTrainers_shouldReturnEmptyList() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .name("Client")
                .phone("380991112233")
                .role(Role.CLIENT)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(trainerClientRepository.findByClient(client))
                .thenReturn(List.of());

        List<TrainerResponse> result = clientService.getTrainers(token);

        assertNotNull(result);

        assertTrue(result.isEmpty());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(trainerClientRepository).findByClient(client);
    }

    @Test
    void getAvailableTrainers_shouldReturnAvailableTrainerList() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .name("Client")
                .phone("380991112233")
                .role(Role.CLIENT)
                .build();

        User connectedTrainer = User.builder()
                .id(2L)
                .name("Connected Trainer")
                .phone("380671111111")
                .role(Role.TRAINER)
                .build();

        User pendingTrainer = User.builder()
                .id(3L)
                .name("Pending Trainer")
                .phone("380672222222")
                .role(Role.TRAINER)
                .build();

        User availableTrainer = User.builder()
                .id(4L)
                .name("Available Trainer")
                .phone("380673333333")
                .role(Role.TRAINER)
                .build();

        TrainerClient trainerClient = TrainerClient.builder()
                .id(10L)
                .client(client)
                .trainer(connectedTrainer)
                .build();

        TrainingRequest request = TrainingRequest.builder()
                .id(20L)
                .client(client)
                .trainer(pendingTrainer)
                .status(RequestStatus.PENDING)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(trainerClientRepository.findByClient(client))
                .thenReturn(List.of(trainerClient));

        when(trainingRequestRepository.findByClient(client))
                .thenReturn(List.of(request));

        when(userRepository.findByRole(Role.TRAINER))
                .thenReturn(List.of(
                        connectedTrainer,
                        pendingTrainer,
                        availableTrainer
                ));

        List<AvailableTrainerResponse> result = clientService.getAvailableTrainers(token);

        assertNotNull(result);

        assertEquals(2, result.size());

        AvailableTrainerResponse pendingResponse =
                result.stream()
                        .filter(t -> t.getId().equals(3L))
                        .findFirst()
                        .orElseThrow();

        assertEquals("Pending Trainer", pendingResponse.getName());
        assertEquals("PENDING", pendingResponse.getRequestStatus());

        AvailableTrainerResponse availableResponse =
                result.stream()
                        .filter(t -> t.getId().equals(4L))
                        .findFirst()
                        .orElseThrow();

        assertEquals("Available Trainer", availableResponse.getName());
        assertEquals("NONE", availableResponse.getRequestStatus());
        assertTrue(result.stream().noneMatch(t -> t.getId().equals(2L)));

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(trainerClientRepository).findByClient(client);
        verify(trainingRequestRepository).findByClient(client);
        verify(userRepository).findByRole(Role.TRAINER);
    }

    @Test
    void getAvailableTrainers_shouldReturnEmptyList() {

        String token = "Bearer jwt-token";

        User client = User.builder()
                .id(1L)
                .name("Client")
                .phone("380991112233")
                .role(Role.CLIENT)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(client));

        when(trainerClientRepository.findByClient(client)).thenReturn(List.of());
        when(trainingRequestRepository.findByClient(client)).thenReturn(List.of());
        when(userRepository.findByRole(Role.TRAINER)).thenReturn(List.of());

        List<AvailableTrainerResponse> result = clientService.getAvailableTrainers(token);

        assertNotNull(result);

        assertTrue(result.isEmpty());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");

        verify(trainerClientRepository).findByClient(client);
        verify(trainingRequestRepository).findByClient(client);
        verify(userRepository).findByRole(Role.TRAINER);
    }
}
