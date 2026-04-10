package com.kdob.piq.user.infrastructure.persistence;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.Hibernate;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity {

    public abstract Long getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id=" + getId() +
                ")";
    }
}
