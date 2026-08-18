package com.scouter.gateway.auth;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.scouter.gateway.user.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Reads the session cookie on every request, resolves it to a valid
 * {@link UserSession} and, if valid, authenticates the request's
 * {@link SecurityContextHolder}. Requests without a valid session are simply
 * left unauthenticated — access control is enforced by SecurityConfig, and
 * unauthenticated access to a protected endpoint results in 401 via the
 * configured AuthenticationEntryPoint.
 */
public class AuthFilter extends OncePerRequestFilter {

    public static final String SESSION_COOKIE_NAME = "session_token";

    private final SessionService sessionService;

    public AuthFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        extractToken(request).ifPresent(rawToken ->
                sessionService.validate(rawToken).ifPresent(session -> authenticate(session)));

        filterChain.doFilter(request, response);
    }

    private void authenticate(UserSession session) {
        User user = session.getUser();

        var authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        sessionService.touch(session);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return Optional.empty();
        }

        for (Cookie cookie : cookies) {
            if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
                return Optional.ofNullable(cookie.getValue());
            }
        }

        return Optional.empty();
    }
}
