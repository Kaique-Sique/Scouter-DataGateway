package com.scouter.gateway.user.favorites.teams;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FavoriteTeamService {

    private final UserFavoriteTeamRepository repository;

    public FavoriteTeamService(UserFavoriteTeamRepository repository) {
        this.repository = repository;
    }

    public void addFavorite(UUID userId, String teamId) {

        if (repository.existsByUserIdAndTeamId(userId, teamId)) {
            return;
        }

        UserFavoriteTeam favorite = new UserFavoriteTeam(
                userId,
                teamId,
                Instant.now()
        );

        repository.save(favorite);
    }

    @Transactional
    public void removeFavorite(UUID userId, String teamId) {
        repository.deleteByUserIdAndTeamId(
                userId,
                teamId
        );
    }
}