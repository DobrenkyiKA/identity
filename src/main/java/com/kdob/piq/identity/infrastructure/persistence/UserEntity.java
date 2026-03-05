package com.kdob.piq.identity.infrastructure.persistence;

import com.kdob.piq.identity.domain.model.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_id_sequence", allocationSize = 50)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "citext")
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles;

    @Column(nullable = false, updatable = false, insertable = false)
    @Generated
    private Instant createdAt;

    protected UserEntity() {}

    public UserEntity(String email, String passwordHash, Set<Role> roles, Instant createdAt) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    @Override
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
}
