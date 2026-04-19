package com.kdob.piq.user.infrastructure.kafka;

import java.util.Set;

public record UserCreatedEvent(
        Long authId,
        String email,
        Set<String> roles
) {
}