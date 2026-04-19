package com.kdob.piq.user.application.service;

import com.kdob.piq.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByAuthId(Long authId);
    void save(User user);
}
