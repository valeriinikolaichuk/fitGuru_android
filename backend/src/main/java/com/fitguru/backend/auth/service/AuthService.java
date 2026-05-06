package com.fitguru.backend.auth.service;

import org.springframework.stereotype.Service;

import com.fitguru.backend.user.repository.UserRepository;
import com.fitguru.backend.auth.dto.RegisterRequest;
import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role; 

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public String login(RegisterRequest request) {

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPasswordHash().equals(request.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return "LOGIN_OK";
    }
}
