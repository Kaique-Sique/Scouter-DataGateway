package com.scouter.gateway.auth;

import java.util.Optional;

import com.scouter.gateway.user.User;

public class Authenticator {
    private AuthService authService;

    public Authenticator(AuthService authService)
    {
        this.authService = authService;
    }

    public Optional<User> authenticate(String credentials) {
        String[] parts = credentials.split("/", 2);

        if (parts.length != 2) {
            return Optional.empty();
        }

        return authService.authenticate(parts[0], parts[1]);
    }

    public Optional<User> authenticateAdmin(String credentials) {
        String[] parts = credentials.split("/", 2);

        if (parts.length != 2) {
            return Optional.empty();
        }

        return authService.authenticateAdmin(parts[0], parts[1]);
    }


    
}