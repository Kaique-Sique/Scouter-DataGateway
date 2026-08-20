package com.scouter.gateway.auth;

public record LoginRequest(
    String email,
    String password
) {}