package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.request.ChangePasswordRequest;
import com.mgnt.warehouse.modal.request.LoginRequest;
import com.mgnt.warehouse.modal.request.SignUpRequest;
import com.mgnt.warehouse.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("signin")
    @Operation(summary = "Login in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @PostMapping("signup")
    @Operation(summary = "Create new user")
    public ResponseEntity<?> signup(@RequestBody @NotNull @Valid SignUpRequest signUpRequest) {
        return accountService.createAccount(signUpRequest);
    }

    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        accountService.changePassword(request);
        return ResponseEntity.accepted().body("Received");
    }
}
