package com.kdob.piq.user.domain;

import com.kdob.piq.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void save(User user);
}
