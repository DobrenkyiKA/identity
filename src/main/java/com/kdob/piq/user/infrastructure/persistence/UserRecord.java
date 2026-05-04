package com.kdob.piq.user.infrastructure.persistence;

import com.kdob.piq.user.domain.model.Role;

import java.time.Instant;
import java.util.Set;

public record UserRecord(Long id, Long authId, String email, Set<Role> roles, Instant createdAt) {
}
