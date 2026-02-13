package com.kdob.piq.identity.application;

import com.kdob.piq.identity.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Test
    void register_user_with_hashed_password() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        when(repo.findByEmail("test@test.com")).thenReturn(java.util.Optional.empty());
        when(encoder.encode("password")).thenReturn("hashed");
        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserService service = new UserService(repo, encoder);

        var user = service.register("test@test.com", "password");

        assertThat(user.getPasswordHash()).isEqualTo("hashed");
    }
}