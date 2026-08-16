package com.scouter.gateway.controller.health;

public record HealthDependency(
    String name,
    String status,
    long latency
) {}