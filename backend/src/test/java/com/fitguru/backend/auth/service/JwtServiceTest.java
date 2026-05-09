package com.fitguru.backend.auth.service;

import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {
    
    private final JwtService jwtService = new JwtService();

    private static final String SECRET =
            "very-very-very-very-secret-key-1234567890";

    @Test
    void generateToken_ShouldGenerateValidToken() {

        User user = User.builder()
                .phone("123456789")
                .role(Role.CLIENT)
                .build();

        String token = jwtService.generateToken(user);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(
                        SECRET.getBytes(StandardCharsets.UTF_8)
                )
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("123456789", claims.getSubject());
        assertEquals("CLIENT", claims.get("role"));
    }
}
