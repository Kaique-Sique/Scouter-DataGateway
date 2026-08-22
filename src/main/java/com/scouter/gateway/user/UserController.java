package com.scouter.gateway.user;

import com.scouter.gateway.auth.AuthService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scouter.gateway.user.favorites.events.FavoriteEventService;
import com.scouter.gateway.user.favorites.events.FavoriteEventResponse;
import com.scouter.gateway.user.favorites.teams.FavoriteTeamService;
import com.scouter.gateway.user.preferences.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserPreferencesService userPreferencesService;
    private final FavoriteEventService favoriteEventService;
    private final FavoriteTeamService favoriteTeamService;

    public UserController(
            AuthService authService,
            UserService userService,
            UserPreferencesService userPreferencesService,
            FavoriteEventService favoriteEventService,
            FavoriteTeamService favoriteTeamService) {

        this.authService = authService;
        this.userService = userService;
        this.userPreferencesService = userPreferencesService;
        this.favoriteEventService = favoriteEventService;
        this.favoriteTeamService = favoriteTeamService;
    }

    private Optional<User> authenticate(String credentials) {
        String[] parts = credentials.split("/", 2);

        if (parts.length != 2) {
            return Optional.empty();
        }

        return authService.authenticate(parts[0], parts[1]);
    }

    // ── Self ──────────────────────────────────────────────────────────────────

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(userService.getById(user.getId())))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PatchMapping("/me/username")
    public ResponseEntity<UserResponse> updateMyUsername(
            @RequestHeader("X-Credentials") String credentials,
            @RequestParam String username) {

        return authenticate(credentials)
                .map(user -> {
                    try {
                        return ResponseEntity.ok(userService.updateUsername(user.getId(), username));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.<UserResponse>badRequest().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PatchMapping("/me/email")
    public ResponseEntity<UserResponse> updateMyEmail(
            @RequestHeader("X-Credentials") String credentials,
            @RequestParam String email) {

        return authenticate(credentials)
                .map(user -> {
                    try {
                        return ResponseEntity.ok(userService.updateEmail(user.getId(), email));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.<UserResponse>badRequest().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    // ── Preferences ───────────────────────────────────────────────────────────

    @GetMapping("/me/preferences")
    public ResponseEntity<UserPreferencesResponse> preferences(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        userPreferencesService.get(user.getId())))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PostMapping("/me/preferences/{eventId}")
    public ResponseEntity<UserPreferencesResponse> setLastEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        userPreferencesService.setLastEvent(user.getId(), eventId)))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    // ── Favorite Events ───────────────────────────────────────────────────────

    @GetMapping("/me/favorites/events")
    public ResponseEntity<FavoriteEventResponse> favoriteEvents(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(
                        favoriteEventService.getFavorites(user.getId())))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PostMapping("/me/favorites/events/add/{eventId}")
    public ResponseEntity<Void> addFavoriteEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        return authenticate(credentials)
                .map(user -> {
                    favoriteEventService.addFavorite(user.getId(), eventId);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @DeleteMapping("/me/favorites/events/remove/{eventId}")
    public ResponseEntity<Void> removeFavoriteEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        return authenticate(credentials)
                .map(user -> {
                    favoriteEventService.removeFavorite(user.getId(), eventId);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    // ── Favorite Teams ────────────────────────────────────────────────────────

    @PostMapping("/me/favorites/teams/{teamId}")
    public ResponseEntity<Void> addFavoriteTeam(
            @PathVariable String teamId,
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> {
                    favoriteTeamService.addFavorite(user.getId(), teamId);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @DeleteMapping("/me/favorites/teams/{teamId}")
    public ResponseEntity<Void> removeFavoriteTeam(
            @PathVariable String teamId,
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> {
                    favoriteTeamService.removeFavorite(user.getId(), teamId);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    // ── Admin ─────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAll(
            @RequestHeader("X-Credentials") String credentials) {

        return authenticate(credentials)
                .map(user -> ResponseEntity.ok(userService.getAll()))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        return authenticate(credentials)
                .map(user -> {
                    try {
                        return ResponseEntity.ok(userService.getById(id));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.<UserResponse>notFound().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivate(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        return authenticate(credentials)
                .map(user -> {
                    try {
                        return ResponseEntity.ok(userService.deactivate(id));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.<UserResponse>notFound().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activate(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        return authenticate(credentials)
                .map(user -> {
                    try {
                        return ResponseEntity.ok(userService.activate(id));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.<UserResponse>notFound().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        return authenticate(credentials)
                .map(user -> {
                    try {
                        userService.delete(id);
                        return ResponseEntity.noContent().<Void>build();
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.<Void>notFound().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}