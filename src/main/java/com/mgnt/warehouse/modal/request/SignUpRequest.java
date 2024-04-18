package com.mgnt.warehouse.modal.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    private String username;
    private String password;
    @Email
    private String email;
    private String phoneNumber;
    private String fullName;
    private String address;

    private Set<String> roles;
}
