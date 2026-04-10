package com.kdob.piq.user.infrastructure.persistence;

import com.kdob.piq.user.domain.model.User;
import com.kdob.piq.user.domain.model.UserMapper;
import com.kdob.piq.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<User> findById(final Long id) {
        return repository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public void save(final User user) {
        final UserEntity userEntity = UserMapper.toEntity(user);
        final UserEntity savedUserEntity = repository.save(userEntity);
        UserMapper.toDomain(savedUserEntity);
    }
}
