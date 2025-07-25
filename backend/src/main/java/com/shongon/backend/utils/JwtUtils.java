package com.shongon.backend.utils;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtils {
    private JwtUtils() {}

    public static UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}
