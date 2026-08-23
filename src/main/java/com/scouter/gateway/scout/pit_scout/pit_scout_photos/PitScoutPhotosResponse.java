package com.scouter.gateway.scout.pit_scout.pit_scout_photos;

import java.time.Instant;
import java.util.UUID;


public record PitScoutPhotosResponse(
    UUID id,
    String imgUrl,
    String description,
    Instant createdAt,
    UUID pitScoutId
) {}