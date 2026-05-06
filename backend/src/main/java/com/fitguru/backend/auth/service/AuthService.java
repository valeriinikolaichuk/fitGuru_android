package com.fitguru.backend.auth.service;

import org.springframework.stereotype.Service;

import com.fitguru.backend.user.repository.UserRepository;
import com.fitguru.backend.auth.dto.LoginRequest;
import com.fitguru.backend.auth.dto.LoginResponse;
import com.fitguru.backend.auth.dto.RegisterRequest;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role; 

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .passwordHash(request.getPassword()) // поки без hash
                .role(Role.CLIENT)
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPasswordHash().equals(request.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}
