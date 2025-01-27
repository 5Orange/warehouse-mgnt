package com.mgnt.warehouse.modal.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mgnt.warehouse.modal.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Size(min = 6, message = "Username must be at least 6 characters")
    @Pattern(regexp = "\\S+$", message = "Username is mandatory, cannot contain space")
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 9, message = "The individual card number is required, min length is 9!")
    private String individualCard;

    @NotNull(message = "Password must not be null")
    @JsonIgnore
    private String password;

    @Email(message = "Email Invalid, please try again.")
    private String email;

    @NotEmpty(message = "Full name must not be empty")
    private String fullName;

    private String address;

    private String phoneNumber;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "active")
    private boolean isActive;

}
