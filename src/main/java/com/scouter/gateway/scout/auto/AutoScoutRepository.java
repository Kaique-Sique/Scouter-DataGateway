package com.scouter.gateway.scout.auto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutoScoutRepository extends JpaRepository<AutoScout, UUID> {

    Optional<AutoScout> findByMatchTeamId(String matchTeamId);

    List<AutoScout> findByUserId(UUID userId);

    List<AutoScout> findByTeamKey(String teamKey);

    List<AutoScout> findByEventKey(String eventKey);

    List<AutoScout> findByMatchKey(String matchKey);

    List<AutoScout> findByTeamKeyAndMatchKey(String teamKey, String matchKey);

    boolean existsByMatchTeamId(String matchTeamId);

    void deleteByMatchTeamId(String matchTeamId);
}