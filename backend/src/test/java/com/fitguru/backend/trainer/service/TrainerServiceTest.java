package com.fitguru.backend.trainer.service;

import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.entity.enums.Role;
import com.fitguru.backend.auth.repository.UserRepository;
import com.fitguru.backend.auth.service.JwtService;
import com.fitguru.backend.request.repository.TrainingRequestRepository;
import com.fitguru.backend.trainer.dto.ClientResponse;
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
class TrainerServiceTest {

    @Mock
    private TrainerClientRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void getClients_shouldReturnClientList() {

        String token = "Bearer jwt-token";

        User trainer = User.builder()
                .id(1L)
                .name("Trainer")
                .phone("380991112233")
                .role(Role.TRAINER)
                .build();

        User client = User.builder()
                .id(2L)
                .name("Client")
                .phone("380671112233")
                .role(Role.CLIENT)
                .build();

        TrainerClient trainerClient = TrainerClient.builder()
                .id(10L)
                .trainer(trainer)
                .client(client)
                .build();

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.of(trainer));

        when(repository.findByTrainer(trainer))
                .thenReturn(List.of(trainerClient));

        List<ClientResponse> result = trainerService.getClients(token);

        assertNotNull(result);
        assertEquals(1, result.size());

        ClientResponse response = result.get(0);

        assertEquals(2L, response.getId());
        assertEquals(10L, response.getTrainerClientId());
        assertEquals("Client", response.getName());
        assertEquals("380671112233", response.getPhone());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(repository).findByTrainer(trainer);
    }

    @Test
    void getClients_shouldThrowWhenUserNotFound() {

        String token = "Bearer jwt-token";

        when(jwtService.extractPhone("jwt-token"))
                .thenReturn("380991112233");

        when(userRepository.findByPhone("380991112233"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> trainerService.getClients(token)
        );

        assertEquals("User not found", exception.getMessage());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verifyNoInteractions(repository);
    }

    @Test
    void getClients_shouldThrowWhenUserIsNotTrainer() {

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

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> trainerService.getClients(token)
        );

        assertEquals("Access denied", exception.getMessage());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");

        verifyNoInteractions(repository);
    }

    @Test
    void getClients_shouldReturnEmptyList() {

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

        when(repository.findByTrainer(trainer))
                .thenReturn(List.of());

        List<ClientResponse> result = trainerService.getClients(token);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(jwtService).extractPhone("jwt-token");
        verify(userRepository).findByPhone("380991112233");
        verify(repository).findByTrainer(trainer);
    }
}
