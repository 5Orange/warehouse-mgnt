package com.mgnt.warehouse.modal.auth;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Getter
public class Role implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleConst name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
