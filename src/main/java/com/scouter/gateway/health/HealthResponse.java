package com.scouter.gateway.health;

// java imports
import java.time.Instant;
import java.util.List;

// local imports
import com.scouter.gateway.build.BuildInfo;


public record HealthResponse(
        String status,
        String service,
        BuildInfo build,
        String version,
        Instant timestamp,
        String javaVersion,
        String springBootVersion,
        String environment,
        List<HealthDependency> dependencies
) {}

