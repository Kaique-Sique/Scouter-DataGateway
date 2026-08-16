package com.scouter.gateway.controller;

public record HealthDependency(
    String name,
    String status,
    long latency
) {}