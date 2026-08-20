package com.scouter.gateway.user.favorites.teams;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_favorite_teams")
public class UserFavoriteTeam {
    
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "team_id")
    private String teamId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public UserFavoriteTeam(){}
    
    public UserFavoriteTeam(
            UUID userId,
            String teamId,
            Instant createdAt) {

        this.userId = userId;
        this.teamId = teamId;
        this.createdAt = createdAt;
    }


    public UUID getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
