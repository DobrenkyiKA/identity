package com.kdob.piq.user.infrastructure.web;

import com.kdob.piq.user.domain.model.User;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record UserResponse(
        Long authId,
        String email,
        Set<String> roles
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.authId(),
                user.email(),
                user.roles().stream().map(Enum::name).collect(Collectors.toSet())
        );
    }
}