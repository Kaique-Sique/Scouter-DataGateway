package com.scouter.gateway.user.favorites.teams;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteTeamRepository
        extends JpaRepository<UserFavoriteTeam, UserFavoriteTeamId> {

    List<UserFavoriteTeam> findByUserId(UUID userId);

    boolean existsByUserIdAndTeamId(UUID userId, String teamId);

    void deleteByUserIdAndTeamId(UUID userId, String teamId);
}