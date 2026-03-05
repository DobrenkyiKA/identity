package com.kdob.piq.identity.domain.model;

import com.kdob.piq.identity.infrastructure.persistence.UserEntity;

public class UserMapper {

    static public UserEntity toEntity(User user) {
        return new UserEntity(user.getEmail(), user.getPasswordHash(), user.getRoles(), user.getCreatedAt());
    }

    static public User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getEmail(), entity.getPasswordHash(), entity.getRoles(), entity.getCreatedAt());
    }
}
