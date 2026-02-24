package com.kdob.piq.identity.infrastructure.persistence;

import com.kdob.piq.identity.domain.model.User;
import com.kdob.piq.identity.domain.model.UserMapper;
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
    public void save(final User user) {
        final UserEntity userEntity = UserMapper.toEntity(user);
        final UserEntity savedUserEntity = repository.save(userEntity);
        UserMapper.toDomain(savedUserEntity);
    }
}
