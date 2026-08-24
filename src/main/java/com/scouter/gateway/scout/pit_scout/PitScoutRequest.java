package com.scouter.gateway.scout.pit_scout;

public record PitScoutRequest(
    String teamKey,
    String eventKey,
    String description
) {}