package com.scouter.gateway.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scouter.gateway.auth.dto.LoginRequest;
import com.scouter.gateway.auth.dto.RegisterRequest;
import com.scouter.gateway.user.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }
}