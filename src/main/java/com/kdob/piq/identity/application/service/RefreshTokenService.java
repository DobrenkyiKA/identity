package com.kdob.piq.identity.application.service;

import com.kdob.piq.identity.domain.model.User;
import com.kdob.piq.identity.infrastructure.persistence.token.RefreshTokenEntity;
import com.kdob.piq.identity.infrastructure.persistence.token.RefreshTokenRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(14);

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final TokenService tokenService;

    public RefreshTokenService(final RefreshTokenRepository refreshTokenRepository, final UserService userService, final TokenService tokenService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Transactional
    public String create(UUID userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                userId,
                Instant.now().plus(REFRESH_TOKEN_TTL)
        );

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    private @NonNull RefreshTokenEntity validateToken(final String token) {
        RefreshTokenEntity rt = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        if (rt.isRevoked() || rt.isExpired()) {
            throw new IllegalArgumentException("Refresh token has expired or been revoked");
        }
        return rt;
    }

    @Transactional
    public String refresh(String tokenToRefresh) {
        RefreshTokenEntity refreshToken = validateToken(tokenToRefresh);
        User user = userService.findById(refreshToken.getUserId());

        return tokenService.generateToken(user);
    }

    @Transactional
    public void revokeAllForUser(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(rt -> refreshTokenRepository.findAllByUserId((rt.getUserId()))
                        .forEach(rte -> {
                            rte.revoke();
                            refreshTokenRepository.save(rte);
                        }));

    }
}
