package com.scouter.gateway.auth;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scouter.gateway.auth.dto.AuthResponse;
import com.scouter.gateway.auth.dto.LoginRequest;
import com.scouter.gateway.auth.dto.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${scouter.environment}")
    private String environment;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthResponse.from(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthService.LoginResult result = authService.login(request);

        ResponseCookie cookie = buildSessionCookie(result.token(), result.expiresAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthResponse.from(result.user()));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(
            @RequestBody RegisterRequest request) {

        var user = authService.registerAdmin(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AuthResponse.from(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = AuthFilter.SESSION_COOKIE_NAME, required = false) String sessionToken) {

        if (sessionToken != null) {
            authService.logout(sessionToken);
        }

        ResponseCookie expiredCookie = ResponseCookie.from(AuthFilter.SESSION_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(isProduction())
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                .build();
    }

    private ResponseCookie buildSessionCookie(String rawToken, Instant expiresAt) {
        long maxAgeSeconds = Math.max(0, Duration.between(Instant.now(), expiresAt).getSeconds());

        return ResponseCookie.from(AuthFilter.SESSION_COOKIE_NAME, rawToken)
                .httpOnly(true)
                .secure(isProduction())
                .sameSite("Strict")
                .path("/")
                .maxAge(maxAgeSeconds)
                .build();
    }

    private boolean isProduction() {
        return "production".equalsIgnoreCase(environment);
    }
}
