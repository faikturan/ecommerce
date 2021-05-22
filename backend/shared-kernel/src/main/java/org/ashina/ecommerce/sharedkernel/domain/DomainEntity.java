package org.ashina.ecommerce.sharedkernel.domain;

import java.io.Serializable;
import java.util.Objects;

public abstract class DomainEntity<ID extends Serializable> {

    protected final ID id;

    protected DomainEntity(ID id) {
        this.id = Objects.requireNonNull(id, "Entity identifier must not be null");
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o) || getClass() != o.getClass()) return false;

        DomainEntity<?> other = (DomainEntity<?>) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
}
