package com.scouter.gateway.scout.auto;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutoScoutService {

    private final AutoScoutRepository repository;

    public AutoScoutService(AutoScoutRepository repository) {
        this.repository = repository;
    }

    public void create(AutoScout scout) {

        if (repository.existsByMatchTeamId(scout.getMatchTeamId())) {
            return;
        }

        repository.save(scout);
    }

    public Optional<AutoScout> findByMatchTeamId(String matchTeamId) {
        return repository.findByMatchTeamId(matchTeamId);
    }

    public List<AutoScout> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    public List<AutoScout> findByTeamKey(String teamKey) {
        return repository.findByTeamKey(teamKey);
    }

    public List<AutoScout> findByEventKey(String eventKey) {
        return repository.findByEventKey(eventKey);
    }

    public List<AutoScout> findByMatchKey(String matchKey) {
        return repository.findByMatchKey(matchKey);
    }

    public List<AutoScout> findByTeamKeyAndMatchKey(
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