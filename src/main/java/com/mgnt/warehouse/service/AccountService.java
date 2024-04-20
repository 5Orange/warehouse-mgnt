package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.request.LoginRequest;
import com.mgnt.warehouse.modal.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<?> createAccount(SignUpRequest request);

    ResponseEntity<?> login(LoginRequest request);
}
