package com.scouter.gateway.user.favorites.events;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserFavoriteEventRepository
        extends JpaRepository<UserFavoriteEvent, UserFavoriteEventId> {

    List<UserFavoriteEvent> findByUserId(UUID userId);

    boolean existsByUserIdAndEventId(UUID userId, String eventId);

    void deleteByUserIdAndEventId(UUID userId, String eventId);
}