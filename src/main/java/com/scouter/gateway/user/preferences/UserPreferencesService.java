package com.scouter.gateway.user.preferences;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPreferencesService {

    private final UserPreferencesRepository repository;

    public UserPreferencesService(UserPreferencesRepository repository) {
        this.repository = repository;
    }

    public UserPreferencesResponse get(UUID userId) {
        return repository.findById(userId)
                .map(preferences -> new UserPreferencesResponse(
                        preferences.getLastEventId()))
                .orElse(new UserPreferencesResponse(null));
    }

    public UserPreferencesResponse setLastEvent(
            UUID userId,
            String eventId) {

        UserPreferences preferences = repository.findById(userId)
                .orElseGet(() -> {
                    UserPreferences newPreferences = new UserPreferences();
                    newPreferences.setUserId(userId);
                    return newPreferences;
                });

        preferences.setLastEventId(eventId);

        repository.save(preferences);

        return new UserPreferencesResponse(
                preferences.getLastEventId());
    }
}