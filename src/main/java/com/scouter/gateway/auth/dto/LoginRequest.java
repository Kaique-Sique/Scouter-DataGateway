package com.scouter.gateway.auth.dto;

public record LoginRequest(
    String email,
    String password
) {}