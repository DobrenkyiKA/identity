package com.kdob.piq.user.application.service;

import com.kdob.piq.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id: [" + userId + "] not found"));
    }
}
