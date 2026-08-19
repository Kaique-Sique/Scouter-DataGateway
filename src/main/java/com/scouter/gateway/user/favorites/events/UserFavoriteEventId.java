package com.scouter.gateway.user.favorites.events;

import java.io.Serializable;
import java.util.UUID;

public class UserFavoriteEventId implements Serializable {

    private UUID userId;
    private String eventId;

    public UserFavoriteEventId() {
    }

    public UserFavoriteEventId(UUID userId, String eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    // getters/setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFavoriteEventId other)) return false;

        return userId.equals(other.userId)
                && eventId.equals(other.eventId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + eventId.hashCode();
    }
}