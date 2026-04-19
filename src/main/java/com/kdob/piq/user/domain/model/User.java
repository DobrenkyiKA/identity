package com.kdob.piq.user.domain.model;

import java.util.Set;

public record User(Long authId, String email, Set<Role> roles) {
}