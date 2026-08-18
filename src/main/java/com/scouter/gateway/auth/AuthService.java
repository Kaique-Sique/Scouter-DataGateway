package com.scouter.gateway.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scouter.gateway.auth.dto.LoginRequest;
import com.scouter.gateway.auth.dto.RegisterRequest;
import com.scouter.gateway.user.User;
import com.scouter.gateway.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already in use");
        }

        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());

        user.setPasswordHash(
                passwordEncoder.encode(request.password()));

        user.setActive(true);
        user.setRole("USER");

        return userRepository.save(user);
    }
}