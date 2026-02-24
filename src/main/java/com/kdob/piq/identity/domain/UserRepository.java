package com.kdob.piq.identity.domain;

import com.kdob.piq.identity.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    void save(User user);
}
