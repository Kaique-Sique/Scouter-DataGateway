package com.scouter.gateway.scout.auto;

import java.math.BigDecimal;

public record AutoScoutRequest(
    String matchTeamId,
    String eventKey,
    String matchKey,
    String teamKey,
    int year,
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