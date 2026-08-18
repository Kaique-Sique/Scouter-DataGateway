package com.scouter.gateway.auth;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scouter.gateway.auth.dto.LoginRequest;
import com.scouter.gateway.auth.dto.RegisterRequest;
import com.scouter.gateway.auth.exception.EmailAlreadyExistsException;
import com.scouter.gateway.auth.exception.InvalidCredentialsException;
import com.scouter.gateway.auth.exception.UsernameAlreadyExistsException;
import com.scouter.gateway.user.User;
import com.scouter.gateway.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SessionService sessionService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            SessionService sessionService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.sessionService = sessionService;
    }

    /**
     * Registro normal.
     * Todo usuário criado por esse método recebe a role USER.
     */
    public User register(RegisterRequest request) {

        validateNewUser(request);

        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setActive(true);
        user.setRole("USER");

        return userRepository.save(user);
    }

    /**
     * Criação de administrador.
     * A autorização para chamar esse método deve ser feita
     * pelo Controller/Security antes de chegar aqui.
     */
    public User registerAdmin(RegisterRequest request) {

        validateNewUser(request);

        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setActive(true);
        user.setRole("ADMIN");

        return userRepository.save(user);
    }

    /**
     * Valida credenciais e cria uma nova sessão.
     */
    public LoginResult login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive()) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {

            throw new InvalidCredentialsException();
        }

        String rawToken = tokenService.generateToken();

        UserSession session =
                sessionService.createSession(user, rawToken);

        return new LoginResult(
                user,
                rawToken,
                session.getExpiresAt()
        );
    }

    /**
     * Remove a sessão associada ao token.
     */
    public void logout(String rawToken) {
        sessionService.revoke(rawToken);
    }

    private void validateNewUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }
    }

    public record LoginResult(
            User user,
            String token,
            Instant expiresAt
    ) {
    }
}
