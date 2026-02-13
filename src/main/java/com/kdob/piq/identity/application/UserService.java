package com.kdob.piq.identity.application;

import com.kdob.piq.identity.domain.Role;
import com.kdob.piq.identity.domain.User;
import com.kdob.piq.identity.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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
                    throw new IllegalStateException("User already exists");
                });

        String passwordHash = passwordEncoder.encode(rawPassword);

        User user = new User(null, email, passwordHash, Set.of(Role.USER), null);
        return userRepository.save(user);
    }
}
