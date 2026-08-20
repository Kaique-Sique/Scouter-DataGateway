package com.scouter.gateway.user.favorites.events;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class FavoriteEventService {

    private final UserFavoriteEventRepository repository;

    public FavoriteEventService(
            UserFavoriteEventRepository repository) {

        this.repository = repository;
    }

    public FavoriteEventResponse getFavorites(UUID userId) {

        List<String> events = repository.findByUserId(userId)
                .stream()
                .map(UserFavoriteEvent::getEventId)
                .toList();

        return new FavoriteEventResponse(events);
    }

    public void addFavorite(UUID userId, String eventId) {

        if (repository.existsByUserIdAndEventId(userId, eventId)) {
            return;
        }

        UserFavoriteEvent favorite = new UserFavoriteEvent(
                userId,
                eventId,
                Instant.now()
        );

        repository.save(favorite);
    }

    @Transactional
    public void removeFavorite(UUID userId, String eventId) {

        repository.deleteByUserIdAndEventId(
                userId,
                eventId
        );
    }
}