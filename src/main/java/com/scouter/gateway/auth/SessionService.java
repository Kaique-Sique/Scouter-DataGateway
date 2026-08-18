package com.scouter.gateway.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scouter.gateway.user.User;

@Service
public class SessionService {

    private final UserSessionRepository sessionRepository;
    private final TokenService tokenService;

    @Value("${scouter.session.ttl-hours:168}")
    private long ttlHours;

    public SessionService(UserSessionRepository sessionRepository, TokenService tokenService) {
        this.sessionRepository = sessionRepository;
        this.tokenService = tokenService;
    }

    /**
     * Creates and persists a new session for the given user using the raw token.
     * Only the token hash is stored.
     */
    public UserSession createSession(User user, String rawToken) {
        String tokenHash = tokenService.hashToken(rawToken);
        Instant expiresAt = Instant.now().plus(ttlHours, ChronoUnit.HOURS);

        UserSession session = new UserSession(user, tokenHash, expiresAt);
        return sessionRepository.save(session);
    }

    /**
     * Resolves a raw token to a valid (non-expired) session, if any.
     */
    public Optional<UserSession> validate(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            return Optional.empty();
        }

        String tokenHash = tokenService.hashToken(rawToken);

        return sessionRepository.findByTokenHash(tokenHash)
                .filter(session -> !session.isExpired());
    }

    /**
     * Updates the last-used timestamp of a session.
     */
    public void touch(UserSession session) {
        session.setLastUsedAt(Instant.now());
        sessionRepository.save(session);
    }

    /**
     * Revokes (deletes) the session associated with the given raw token.
     */
    public void revoke(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            return;
        }

        String tokenHash = tokenService.hashToken(rawToken);
        sessionRepository.deleteByTokenHash(tokenHash);
    }

    public long getTtlHours() {
        return ttlHours;
    }
}
