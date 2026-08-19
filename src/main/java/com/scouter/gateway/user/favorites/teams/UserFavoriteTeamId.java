package com.scouter.gateway.user.favorites.teams;

import java.io.Serializable;
import java.util.UUID;

public class UserFavoriteTeamId implements Serializable {

    private UUID userId;
    private String teamId;

    public UserFavoriteTeamId(){}

    public UserFavoriteTeamId(UUID userId, String teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    // getters/setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String eventId) {
        this.teamId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFavoriteTeamId other)) return false;

        return userId.equals(other.userId)
                && teamId.equals(other.teamId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + teamId.hashCode();
    }
}