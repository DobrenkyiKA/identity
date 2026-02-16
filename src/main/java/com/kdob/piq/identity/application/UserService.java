package com.kdob.piq.identity.application;

import com.kdob.piq.identity.domain.Role;
import com.kdob.piq.identity.domain.User;
import com.kdob.piq.identity.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String rawPassword) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalStateException("User with email: [" + user.getEmail() + "] already exists");
                });

        String passwordHash = passwordEncoder.encode(rawPassword);

        User user = new User(null, email, passwordHash, Set.of(Role.USER), null);
        return userRepository.save(user);
    }

    public User authenticate(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPasswordHash()))
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));
    }

    public User findById(final UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id: [" + userId + "] not found"));
    }
}
