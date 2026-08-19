package com.scouter.gateway.user;

import com.scouter.gateway.auth.AuthService;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scouter.gateway.user.favorites.events.FavoriteEventService;
import com.scouter.gateway.user.favorites.events.FavoriteEventResponse;
import com.scouter.gateway.user.preferences.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final UserPreferencesService userPreferencesService;
    private final FavoriteEventService favoriteEventService;

    public UserController(
            AuthService authService,
            UserPreferencesService userPreferencesService,
            FavoriteEventService favoriteEventService) {

        this.authService = authService;
        this.userPreferencesService = userPreferencesService;
        this.favoriteEventService = favoriteEventService;
    }

    private Optional<User> authenticate(String credentials) {
        String[] parts = credentials.split("/", 2);

        if (parts.length != 2) {
            return Optional.empty();
        }

        return authService.authenticate(parts[0], parts[1]);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        new UserResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.isActive())))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/me/preferences")
    public ResponseEntity<UserPreferencesResponse> preferences(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        userPreferencesService.get(user.getId())))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PostMapping("/me/preferences/{eventId}")
    public ResponseEntity<UserPreferencesResponse> preferences(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        userPreferencesService.setLastEvent(
                                user.getId(),
                                eventId)))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/me/favorites/events")
    public ResponseEntity<FavoriteEventResponse> favoriteEvents(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        favoriteEventService.getFavorites(user.getId())))
                .orElseGet(() -> ResponseEntity
                        .status(401)
                        .build());
    }

    @PostMapping("/me/favorites/events/add/{eventId}")
    public ResponseEntity<Void> addFavoriteEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        return authenticate(credentials)
                .map(user -> {
                    favoriteEventService.addFavorite(
                            user.getId(),
                            eventId);

                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity
                        .status(401)
                        .build());
    }

    @DeleteMapping("/me/favorites/events/remove/{eventId}")
    public ResponseEntity<Void> removeFavoriteEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        return authenticate(credentials)
                .map(user -> {
                    favoriteEventService.removeFavorite(
                            user.getId(),
                            eventId);

                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity
                        .status(401)
                        .build());
    }
}