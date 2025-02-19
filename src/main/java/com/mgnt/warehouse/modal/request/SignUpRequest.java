package com.mgnt.warehouse.modal.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    @Size(min = 6, message = "Username must be at least 6 characters")
    @Pattern(regexp = "\\S+$", message = "Username is mandatory, cannot contain space")
    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "Password must not be null")
    @NotEmpty
    private String password;
    @Email(message = "Invalid email")
    private String email;
    @NotNull
    @NotEmpty(message = "Phome number is required")
    private String phoneNumber;
    @NotEmpty(message = "Full name must not be empty")
    private String fullName;
    private String address;
    @NotNull
    @NotEmpty
    @Size(min = 9, message = "The individual card number is required, min length is 9!")
    private String individualCard;

    private Set<String> roles;
}
