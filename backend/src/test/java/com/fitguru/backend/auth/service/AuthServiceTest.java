package com.fitguru.backend.auth.service;

import com.fitguru.backend.auth.dto.LoginRequest;
import com.fitguru.backend.auth.dto.LoginResponse;
import com.fitguru.backend.auth.dto.RegisterRequest;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role;
import com.fitguru.backend.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {

        registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setPhone("123456789");
        registerRequest.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setPhone("123456789");
        loginRequest.setPassword("password");

        user = User.builder()
                .name("John")
                .phone("123456789")
                .passwordHash("encodedPassword")
                .role(Role.CLIENT)
                .build();
    }

    @Test
    void register_ShouldSaveUser() {

        when(userRepository.existsByPhone(registerRequest.getPhone()))
                .thenReturn(false);

        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encodedPassword");

        authService.register(registerRequest);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUserExists() {

        when(userRepository.existsByPhone(registerRequest.getPhone()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(registerRequest)
        );

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void login_ShouldReturnToken() {

        when(userRepository.findByPhone(loginRequest.getPhone()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPasswordHash()
        )).thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        LoginResponse response = authService.login(loginRequest);

        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {

        when(userRepository.findByPhone(loginRequest.getPhone()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(loginRequest)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void login_ShouldThrowException_WhenWrongPassword() {

        user.setPasswordHash("wrong-password");

        when(userRepository.findByPhone(loginRequest.getPhone()))
                .thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(loginRequest)
        );

        assertEquals("Wrong password", exception.getMessage());
    }
}
