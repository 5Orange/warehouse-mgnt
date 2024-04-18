package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.auth.Role;
import com.mgnt.warehouse.modal.auth.RoleConst;
import com.mgnt.warehouse.modal.request.LoginRequest;
import com.mgnt.warehouse.modal.request.SignUpRequest;
import com.mgnt.warehouse.repository.RoleRepository;
import com.mgnt.warehouse.service.AccountService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        roleRepository.save(Role.builder().name(RoleConst.ROLE_USER).build());
        roleRepository.save(Role.builder().name(RoleConst.ROLE_ADMIN).build());
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody @NotNull SignUpRequest signUpRequest) {
        return accountService.createAccount(signUpRequest);
    }
}
