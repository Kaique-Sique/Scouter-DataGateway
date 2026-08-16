package com.scouter.gateway.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public HealthResponse response()
    {
        return new HealthResponse(
            "UP",
            "Scouter Gateway",
            "0.0.1",
            Instant.now(),
            "17.0.16",
            "4.1.0",
            "development",
            List.of(
                new HealthDependency(
                    "Supabase-db",
                    "not-implemented",
                    0)
            )
        );
    }
}
