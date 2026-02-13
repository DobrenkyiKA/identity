package com.kdob.piq.identity.persistence;

import com.kdob.piq.identity.domain.User;
import com.kdob.piq.identity.domain.UserMapper;
import com.kdob.piq.identity.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository repository;

    public JpaUserRepository(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return repository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(final UUID id) {
        return repository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public User save(final User user) {
        final UserEntity userEntity = UserMapper.toEntity(user);
        final UserEntity savedUserEntity = repository.save(userEntity);
        return UserMapper.toDomain(savedUserEntity);
    }
}
