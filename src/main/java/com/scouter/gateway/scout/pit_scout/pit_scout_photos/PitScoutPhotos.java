package com.scouter.gateway.scout.pit_scout.pit_scout_photos;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

import com.scouter.gateway.scout.pit_scout.PitScout;

@Entity
@Table(name = "pit_scout_photos")
public class PitScoutPhotos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "img_url", nullable = false, columnDefinition = "TEXT")
    private String imgUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pit_scout_id", nullable = false)
    private PitScout pitScout;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public UUID getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PitScout getPitScout() {
        return pitScout;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPitScout(PitScout pitScout) {
        this.pitScout = pitScout;
    }
}