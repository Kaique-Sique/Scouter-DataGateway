package com.scouter.gateway.scout.auto;

import java.math.BigDecimal;
import java.util.UUID;

public record AutoScoutResponse(
    String matchTeamId,
    String eventKey,
    String matchKey,
    String teamKey,
    int year,
    UUID userId,
    int l1,
    int l2,
    int l3,
    int l4,
    int coralMisseds,
    BigDecimal coralPrecision,
    int algaeRemoved,
    int algaeNet,
    int algaeProcessor,
    String regionScored,
    int score,
    boolean startline,
    String notes
) {}