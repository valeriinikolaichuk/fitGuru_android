package com.fitguru.backend.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitguru.backend.user.repository.UserRepository;
import com.fitguru.backend.auth.dto.LoginRequest;
import com.fitguru.backend.auth.dto.LoginResponse;
import com.fitguru.backend.auth.dto.RegisterRequest;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role; 

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
        UserRepository userRepository, 
        PasswordEncoder passwordEncoder, 
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPasswordHash()
        )) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}
