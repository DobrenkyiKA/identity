package com.kdob.piq.user.application.service;

import com.kdob.piq.user.domain.model.Role;
import com.kdob.piq.user.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    private final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a user profile if one doesn't already exist for this authId.
     * This method is idempotent — safe to call multiple times with the same data.
     * <p>
     * Idempotency is critical because Kafka provides at-least-once delivery,
     * meaning the same event might be processed more than once.
     */
    @Transactional
    public void createProfileIfNotExists(Long authId, String email, Set<String> roles) {
        if (userRepository.findByEmail(email).isPresent()) {
            logger.info("User profile for authId=[{}] already exist", authId);
            return;
        }

        Set<Role> roleSet = roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        User user = new User(authId, email, roleSet);
        userRepository.save(user);
    }
}