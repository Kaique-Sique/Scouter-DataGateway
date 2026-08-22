package com.scouter.gateway.scout.teleop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeleopScoutRepository extends JpaRepository<TeleopScout, UUID> {

    Optional<TeleopScout> findByMatchTeamId(String matchTeamId);

    List<TeleopScout> findByUserId(UUID userId);

    List<TeleopScout> findByTeamKey(String teamKey);

    List<TeleopScout> findByEventKey(String eventKey);

    List<TeleopScout> findByMatchKey(String matchKey);

    List<TeleopScout> findByTeamKeyAndMatchKey(String teamKey, String matchKey);

    boolean existsByMatchTeamId(String matchTeamId);

    void deleteByMatchTeamId(String matchTeamId);
}