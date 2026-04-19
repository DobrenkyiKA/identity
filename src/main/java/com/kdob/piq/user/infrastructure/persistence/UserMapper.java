package com.kdob.piq.user.infrastructure.persistence;

import com.kdob.piq.user.domain.model.User;

public class UserMapper {

    static public UserEntity toEntity(User user) {
        return new UserEntity(user.authId(), user.email(), user.roles());
    }

    static public User toDomain(UserEntity entity) {
        return new User(entity.getAuthId(), entity.getEmail(), entity.getRoles());
    }
}
