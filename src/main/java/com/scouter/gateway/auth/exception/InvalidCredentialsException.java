package com.scouter.gateway.auth.exception;

/**
 * Thrown for any failed login attempt (unknown email, wrong password,
 * or inactive user). Intentionally generic to avoid leaking which
 * condition failed.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
