package com.scouter.gateway.auth.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username already in use: " + username);
    }
}
