package com.kdob.piq.user.infrastructure.config;

import com.kdob.piq.user.domain.model.Role;
import com.kdob.piq.user.domain.model.User;
import com.kdob.piq.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Development initializer: ensures a default admin account exists.
 * <p>
 * Email: admin@piq.local
 * Password: admin123
 */
@Component
@Profile({"default", "dev", "local"})
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private static final String DEFAULT_ADMIN_EMAIL = "admin@admin.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";
    private static final String DEFAULT_USER_EMAIL = "user@user.com";
    private static final String DEFAULT_USER_PASSWORD = "user";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        ensurePresentInTheDb(DEFAULT_ADMIN_EMAIL, DEFAULT_ADMIN_PASSWORD, Role.ADMIN);
        ensurePresentInTheDb(DEFAULT_USER_EMAIL, DEFAULT_USER_PASSWORD, Role.USER);
    }

    private void ensurePresentInTheDb(String email, String password, Role role) {
        Optional<User> maybeExistingAdmin = userRepository.findByEmail(email);
        if (maybeExistingAdmin.isEmpty()) {

            Set<Role> roles = new HashSet<>();
            roles.add(role);

            User admin = new User(
                    null,
                    email,
                    passwordEncoder.encode(password),
                    roles,
                    Instant.now()
            );
            userRepository.save(admin);
            log.info("[DataInitializer] Created default user: [{}] / [{}]", email, password);
            return;
        }

        User existing = maybeExistingAdmin.get();
        if (!existing.getRoles().contains(role)) {
            Set<Role> roles = new HashSet<>(existing.getRoles());
            roles.add(role);
            User promoted = new User(
                    existing.getId(),
                    existing.getEmail(),
                    existing.getPasswordHash(),
                    roles,
                    existing.getCreatedAt()
            );
            userRepository.save(promoted);
            log.info("[DataInitializer] Promoted existing user [{}] to role [{}]", existing.getEmail(), role);
        } else {
            log.info("[DataInitializer] User already exists: [{}]", existing.getEmail());
        }
    }
}
