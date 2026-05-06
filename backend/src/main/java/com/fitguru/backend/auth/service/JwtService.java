package com.fitguru.backend.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.fitguru.backend.user.entity.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
        "very-very-very-very-secret-key-1234567890";

    private final Key key = Keys.hmacShaKeyFor(
        SECRET.getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getPhone())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }
}
