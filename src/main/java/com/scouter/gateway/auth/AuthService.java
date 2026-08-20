package com.scouter.gateway.auth;

import com.scouter.gateway.user.User;
import com.scouter.gateway.user.UserRepository;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(
        UserRepository userRepository,
        BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {

        if (request.username() == null || request.username().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(
            passwordEncoder.encode(request.password())
        );
        user.setActive(true);

        userRepository.save(user);

        return new RegisterResponse(true);
    }

    public LoginResponse login(LoginRequest request) {

        if (request.email() == null || request.email().isBlank()) {
            return new LoginResponse(false);
        }

        if (request.password() == null || request.password().isBlank()) {
            return new LoginResponse(false);
        }

        return userRepository.findByEmail(request.email())
            .filter(User::isActive)
            .map(user -> passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
            ))
            .map(LoginResponse::new)
            .orElse(new LoginResponse(false));
    }

    public Optional<User> authenticate(String email, String password) {
    return userRepository.findByEmail(email)
        .filter(User::isActive)
        .filter(user -> passwordEncoder.matches(
            password,
            user.getPasswordHash()
        ));
}
}