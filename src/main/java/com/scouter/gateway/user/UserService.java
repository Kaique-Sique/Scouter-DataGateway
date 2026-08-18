package com.scouter.gateway.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already in use");
        }

        return repository.save(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User update(UUID id, User updatedUser) {
        User user = findById(id);

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        user.setActive(updatedUser.isActive());

        return repository.save(user);
    }

    public void delete(UUID id) {
        User user = findById(id);
        repository.delete(user);
    }
}