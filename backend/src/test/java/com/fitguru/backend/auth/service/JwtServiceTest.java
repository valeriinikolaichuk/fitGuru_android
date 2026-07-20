package com.fitguru.backend.auth.service;
import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.entity.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            "very-very-very-very-secret-key-1234567890";

    private final Key key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    private User user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        user = User.builder()
                .phone("380991112233")
                .role(Role.CLIENT)
                .build();
    }

    @Test
    void generateAccessToken_shouldCreateValidToken() {

        String token = jwtService.generateAccessToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        assertEquals(
                "380991112233",
                claims.getSubject()
        );

        assertEquals(
                "CLIENT",
                claims.get("role")
        );

        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void generateRefreshToken_shouldCreateValidToken() {

        String token = jwtService.generateRefreshToken(user);

        assertNotNull(token);

        Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        assertEquals(
                "380991112233",
                claims.getSubject()
        );

        assertNotNull(claims.getExpiration());
    }

    @Test
    void extractPhone_shouldReturnUserPhone() {

        String token = jwtService.generateAccessToken(user);
        String phone = jwtService.extractPhone(token);

        assertEquals(
                "380991112233",
                phone
        );
    }

    @Test
    void extractPhone_shouldFailWithInvalidToken() {

        String invalidToken = "invalid.jwt.token";

        assertThrows(
                Exception.class,
                () -> jwtService.extractPhone(invalidToken)
        );
    }
}
