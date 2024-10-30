package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.auth.Role;
import com.mgnt.warehouse.modal.auth.RoleConst;
import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.modal.mapper.SigninRequestMapper;
import com.mgnt.warehouse.modal.request.LoginRequest;
import com.mgnt.warehouse.modal.request.SignUpRequest;
import com.mgnt.warehouse.modal.response.ErrorResponse;
import com.mgnt.warehouse.modal.response.LoginResponse;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.repository.RoleRepository;
import com.mgnt.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final String ROLE_NOT_FOUND = "Error: Role is not found.";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final SigninRequestMapper signinRequestMapper;
    private final JwtService jwtService;

    public ResponseEntity<?> createAccount(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsernameOrPhoneNumber(signUpRequest.getUsername(), signUpRequest.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(ErrorResponse.builder().message("username already exist").build());
        }
        User user = signinRequestMapper.toUser(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleConst.ROLE_USER)
                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                var r = switch (role) {
                    case "admin" -> roleRepository
                        .findByName(RoleConst.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
                    default -> roleRepository
                        .findByName(RoleConst.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
                };
                roles.add(r);

            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok().body(
            SuccessResponse.builder().message("User registered successfully!").data(user.getUsername()).build());
    }

    public ResponseEntity<?> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        return Optional.ofNullable(user)
            .map(u -> {
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtService.generateJwtToken(authentication);
                return ResponseEntity.ok().body(LoginResponse.builder().token(jwt).build());
            })
            .orElse(ResponseEntity.badRequest().body(LoginResponse.builder().error("User not found!").build()));
    }
}
