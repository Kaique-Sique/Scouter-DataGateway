package com.scouter.gateway.scout.pit_scout;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.scouter.gateway.scout.pit_scout.pit_scout_photos.PitScoutPhotos;

@Entity
@Table(name = "pit_scout")
public class PitScout {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "team_key", nullable = false, length = 50)
    private String teamKey;

    @Column(name = "event_key", nullable = false, length = 50)
    private String eventKey;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "pitScout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PitScoutPhotos> photos = new ArrayList<>();

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public UUID getId() {
        return id;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public String getEventKey() {
        return eventKey;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public List<PitScoutPhotos> getPhotos() {
        return photos;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotos(List<PitScoutPhotos> photos) {
        this.photos = photos;
    }
}