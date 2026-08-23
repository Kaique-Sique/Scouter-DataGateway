package com.scouter.gateway.scout.teleop;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeleopScoutService {

    private final TeleopScoutRepository repository;

    public TeleopScoutService(TeleopScoutRepository repository) {
        this.repository = repository;
    }

    public void create(TeleopScout scout) {

        if (repository.existsByMatchTeamId(scout.getMatchTeamId())) {
            return;
        }

        repository.save(scout);
    }

    public Optional<TeleopScout> findByMatchTeamId(String matchTeamId) {
        return repository.findByMatchTeamId(matchTeamId);
    }

    public List<TeleopScout> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    public List<TeleopScout> findByTeamKey(String teamKey) {
        return repository.findByTeamKey(teamKey);
    }

    public List<TeleopScout> findByEventKey(String eventKey) {
        return repository.findByEventKey(eventKey);
    }

    public List<TeleopScout> findByMatchKey(String matchKey) {
        return repository.findByMatchKey(matchKey);
    }

    public List<TeleopScout> findByTeamKeyAndMatchKey(
            String teamKey,
            String matchKey) {

        return repository.findByTeamKeyAndMatchKey(teamKey, matchKey);
    }

    public boolean existsByMatchTeamId(String matchTeamId) {
        return repository.existsByMatchTeamId(matchTeamId);
    }

    public void deleteByMatchTeamId(String matchTeamId) {
        repository.deleteByMatchTeamId(matchTeamId);
    }
}