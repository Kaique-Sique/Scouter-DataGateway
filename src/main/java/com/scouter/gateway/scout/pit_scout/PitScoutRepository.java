package com.scouter.gateway.scout.pit_scout;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PitScoutRepository extends JpaRepository<PitScout, UUID> {
    List<PitScout> findByUserId(UUID userId);

    List<PitScout> findByTeamKey(String teamKey);

    List<PitScout> findByEventKey(String eventKey);

    List<PitScout> findByTeamKeyAndEventKey(String teamKey, String eventKey);

    boolean existsByTeamKeyAndEventKey(String teamKey, String eventKey);

    void deleteByTeamKeyAndEventKey(String teamKey, String eventKey);
}