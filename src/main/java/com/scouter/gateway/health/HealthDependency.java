package com.scouter.gateway.health;

public record HealthDependency(
    String name,
    String status,
    long latency
) {}