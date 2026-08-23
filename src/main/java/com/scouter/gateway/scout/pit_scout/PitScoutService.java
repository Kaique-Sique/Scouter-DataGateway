package com.scouter.gateway.scout.pit_scout;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PitScoutService {

    private final PitScoutRepository pitScoutRepository;

    public PitScoutService(PitScoutRepository pitScoutRepository) {
        this.pitScoutRepository = pitScoutRepository;
    }

    public PitScoutResponse create(PitScout pitScout) {
        return toResponse(pitScoutRepository.save(pitScout));
    }

    public PitScout getEntityById(UUID id) {
        return pitScoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pit scout not found"));
    }

    public List<PitScoutResponse> findByUserId(UUID userId) {
        return pitScoutRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PitScoutResponse> findByTeamKey(String teamKey) {
        return pitScoutRepository.findByTeamKey(teamKey)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PitScoutResponse> findByEventKey(String eventKey) {
        return pitScoutRepository.findByEventKey(eventKey)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PitScoutResponse> findByTeamKeyAndEventKey(String teamKey, String eventKey) {
        return pitScoutRepository.findByTeamKeyAndEventKey(teamKey, eventKey)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public boolean existsByTeamKeyAndEventKey(String teamKey, String eventKey) {
        return pitScoutRepository.existsByTeamKeyAndEventKey(teamKey, eventKey);
    }

    public void deleteByTeamKeyAndEventKey(String teamKey, String eventKey) {
        pitScoutRepository.deleteByTeamKeyAndEventKey(teamKey, eventKey);
    }

    private PitScoutResponse toResponse(PitScout pitScout) {
        return new PitScoutResponse(
                pitScout.getId(),
                pitScout.getTeamKey(),
                pitScout.getEventKey(),
                pitScout.getUserId(),
                pitScout.getDescription(),
                pitScout.getCreatedAt(),
                pitScout.getUpdatedAt()
        );
    }
}