package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.request.LoginRequest;
import com.mgnt.warehouse.modal.request.SignUpRequest;
import com.mgnt.warehouse.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody @NotNull SignUpRequest signUpRequest) {
        return accountService.createAccount(signUpRequest);
    }
}
