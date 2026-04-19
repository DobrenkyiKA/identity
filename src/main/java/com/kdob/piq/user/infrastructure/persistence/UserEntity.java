package com.kdob.piq.user.infrastructure.persistence;

import com.kdob.piq.user.domain.model.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_id_sequence", allocationSize = 50)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long authId;

    @Column(nullable = false, unique = true, columnDefinition = "citext")
    private String email;

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

    public UserEntity(Long authId, String email, Set<Role> roles) {
        this.authId = authId;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getAuthId() {
        return authId;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}