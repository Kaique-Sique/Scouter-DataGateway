package com.scouter.gateway.scout.pit_scout;

import java.time.Instant;
import java.util.UUID;

public record PitScoutResponse(
    UUID id,
    String teamKey,
    String eventKey,
    UUID userId,
    String description,
    Instant createdAt,
    Instant updatedAt
) {}