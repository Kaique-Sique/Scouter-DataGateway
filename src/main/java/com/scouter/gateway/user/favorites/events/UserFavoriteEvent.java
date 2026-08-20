package com.scouter.gateway.user.favorites.events;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_favorite_events")
@IdClass(UserFavoriteEventId.class)
public class UserFavoriteEvent {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "event_id")
    private String eventId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public UserFavoriteEvent() {
    }

    public UserFavoriteEvent(
            UUID userId,
            String eventId,
            Instant createdAt) {

        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}