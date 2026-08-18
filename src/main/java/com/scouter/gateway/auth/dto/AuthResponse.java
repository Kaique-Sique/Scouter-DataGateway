package com.scouter.gateway.auth.dto;

import java.util.UUID;

import com.scouter.gateway.user.User;

/**
 * Safe, public representation of an authenticated user.
 * Never includes passwordHash or any session/token material.
 */
public record AuthResponse(
        UUID id,
        String username,
        String email,
        String role) {

    public static AuthResponse from(User user) {
        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }
}
