package com.fitguru.backend.auth.service;

import com.fitguru.backend.auth.dto.*;
import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.entity.enums.Role;
import com.fitguru.backend.auth.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldCreateNewUser() {

        RegisterRequest request = new RegisterRequest();
        request.setName("John");
        request.setPhone("123456789");
        request.setPassword("password");
        request.setRole("CLIENT");

        when(userRepository.existsByPhone("123456789"))
                .thenReturn(false);

        when(passwordEncoder.encode("password"))
                .thenReturn("hashedPassword");

        when(jwtService.generateAccessToken(any(User.class)))
                .thenReturn("access-token");

        when(jwtService.generateRefreshToken(any(User.class)))
                .thenReturn("refresh-token");

        LoginResponse response =
                authService.register(request);

        assertEquals(
                "access-token",
                response.getAccessToken()
        );

        assertEquals(
                "refresh-token",
                response.getRefreshToken()
        );

        assertEquals(
                "CLIENT",
                response.getRole()
        );

        verify(userRepository)
                .save(any(User.class));
    }

    @Test
    void register_shouldFail_whenUserExists() {

        RegisterRequest request = new RegisterRequest();
        request.setPhone("123456789");

        when(userRepository.existsByPhone("123456789"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authService.register(request)
                );

        assertEquals(
                "User already exists",
                exception.getMessage()
        );

        verify(userRepository, never())
                .save(any());
    }

    @Test
    void login_shouldReturnTokens_whenCredentialsCorrect() {

        LoginRequest request = new LoginRequest();
        request.setPhone("123456789");
        request.setPassword("password");

        User user = User.builder()
                .phone("123456789")
                .passwordHash("hash")
                .role(Role.CLIENT)
                .build();

        when(userRepository.findByPhone("123456789"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "password",
                "hash"
        ))
        .thenReturn(true);

        when(jwtService.generateAccessToken(user))
                .thenReturn("access");

        when(jwtService.generateRefreshToken(user))
                .thenReturn("refresh");

        LoginResponse response =
                authService.login(request);

        assertEquals(
                "access",
                response.getAccessToken()
        );

        verify(userRepository)
                .save(user);
    }

    @Test
    void login_shouldFail_whenPasswordWrong() {

        LoginRequest request = new LoginRequest();

        request.setPhone("123");
        request.setPassword("wrong");

        User user = User.builder()
                .phone("123")
                .passwordHash("hash")
                .role(Role.CLIENT)
                .build();

        when(userRepository.findByPhone("123"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong",
                "hash"
        ))
        .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authService.login(request)
                );

        assertEquals(
                "Wrong password",
                exception.getMessage()
        );
    }

    @Test
    void refresh_shouldGenerateNewTokens() throws Exception {

        RefreshRequest request = new RefreshRequest();

        Field field = RefreshRequest.class
                .getDeclaredField("refreshToken");

        field.setAccessible(true);
        field.set(request, "old-refresh-token");

        User user = User.builder()
                .phone("123456789")
                .role(Role.CLIENT)
                .build();

        when(jwtService.extractPhone("old-refresh-token"))
                .thenReturn("123456789");

        when(userRepository.findByPhone("123456789"))
                .thenReturn(Optional.of(user));

        when(jwtService.generateAccessToken(user))
                .thenReturn("new-access");

        when(jwtService.generateRefreshToken(user))
                .thenReturn("new-refresh");

        LoginResponse response =
                authService.refresh(request);

        assertEquals(
                "new-access",
                response.getAccessToken()
        );

        assertEquals(
                "new-refresh",
                response.getRefreshToken()
        );
    }
}
