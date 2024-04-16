package com.mgnt.warehouse.modal.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 6, message = "Username must be at least 6 characters")
    @Pattern(regexp = "\\S+$", message = "Username is mandatory, cannot contain space")
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;

    @Email(message = "Email Invalid, please try again.")
    private String email;
    private Long phoneNumber;

    @NotEmpty(message = "Full name must not be empty")
    private String fullName;
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    public User() {
    }

    public User(String username, String password, @Email(message = "Email Invalid, please try again.") String email,
                Long phoneNumber, String fullName, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }
}
