package com.kdob.piq.identity.domain;

import com.kdob.piq.identity.persistence.UserEntity;

public class UserMapper {

    static public UserEntity toEntity(User user) {
        return new UserEntity(null, user.getEmail(), user.getPasswordHash(), user.getRoles(), null);
    }

    static public User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getEmail(), entity.getPasswordHash(), entity.getRoles(), entity.getCreatedAt());
    }
}
