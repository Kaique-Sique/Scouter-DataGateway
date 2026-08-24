package com.scouter.gateway.user;

import com.scouter.gateway.auth.AuthService;
import com.scouter.gateway.auth.Authenticator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
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
    private final Authenticator authenticator;

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

        authenticator = new Authenticator(this.authService);
    }

    // ── Self ──────────────────────────────────────────────────────────────────

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(
            @RequestHeader("X-Credentials") String credentials) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userService.getById(userOpt.get().getId()));
    }

    @PatchMapping("/me/username")
    public ResponseEntity<UserResponse> updateMyUsername(
            @RequestHeader("X-Credentials") String credentials,
            @RequestParam String username) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return ResponseEntity.ok(userService.updateUsername(userOpt.get().getId(), username));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/me/email")
    public ResponseEntity<UserResponse> updateMyEmail(
            @RequestHeader("X-Credentials") String credentials,
            @RequestParam String email) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return ResponseEntity.ok(userService.updateEmail(userOpt.get().getId(), email));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ── Preferences ───────────────────────────────────────────────────────────

    @GetMapping("/me/preferences")
    public ResponseEntity<UserPreferencesResponse> preferences(
            @RequestHeader("X-Credentials") String credentials) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userPreferencesService.get(userOpt.get().getId()));
    }

    @PostMapping("/me/preferences/{eventId}")
    public ResponseEntity<UserPreferencesResponse> setLastEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userPreferencesService.setLastEvent(userOpt.get().getId(), eventId));
    }

    // ── Favorite Events ───────────────────────────────────────────────────────

    @GetMapping("/me/favorites/events")
    public ResponseEntity<FavoriteEventResponse> favoriteEvents(
            @RequestHeader("X-Credentials") String credentials) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(favoriteEventService.getFavorites(userOpt.get().getId()));
    }

    @PostMapping("/me/favorites/events/add/{eventId}")
    public ResponseEntity<Void> addFavoriteEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        favoriteEventService.addFavorite(userOpt.get().getId(), eventId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/favorites/events/remove/{eventId}")
    public ResponseEntity<Void> removeFavoriteEvent(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        favoriteEventService.removeFavorite(userOpt.get().getId(), eventId);
        return ResponseEntity.noContent().build();
    }

    // ── Favorite Teams ────────────────────────────────────────────────────────

    @PostMapping("/me/favorites/teams/{teamId}")
    public ResponseEntity<Void> addFavoriteTeam(
            @PathVariable String teamId,
            @RequestHeader("X-Credentials") String credentials) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        favoriteTeamService.addFavorite(userOpt.get().getId(), teamId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/favorites/teams/{teamId}")
    public ResponseEntity<Void> removeFavoriteTeam(
            @PathVariable String teamId,
            @RequestHeader("X-Credentials") String credentials) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        favoriteTeamService.removeFavorite(userOpt.get().getId(), teamId);
        return ResponseEntity.noContent().build();
    }

    // ── Admin ─────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAll(
            @RequestHeader("X-Credentials") String credentials) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return ResponseEntity.ok(userService.getById(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getByUsername(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String username) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return ResponseEntity.ok(userService.getByUsername(username));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("username/{username}/desactivate")
    public ResponseEntity<UserResponse> desactivate(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String username) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            UUID id = userService.getByUsername(username).id();
            return ResponseEntity.ok(userService.desactivate(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("username/{username}/activate")
    public ResponseEntity<UserResponse> activate(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String username) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            UUID id = userService.getByUsername(username).id();
            return ResponseEntity.ok(userService.activate(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<UserResponse> desactivate(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return ResponseEntity.ok(userService.desactivate(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activate(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return ResponseEntity.ok(userService.activate(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID id) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}