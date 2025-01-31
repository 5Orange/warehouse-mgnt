package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.auth.Role;
import com.mgnt.warehouse.modal.auth.RoleConst;
import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.SigninRequestMapper;
import com.mgnt.warehouse.modal.request.ChangePasswordRequest;
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
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        if (userRepository.existsByUsernameOrPhoneNumberOrEmail(signUpRequest.getUsername(),
                signUpRequest.getPhoneNumber(), signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse.builder()
                            .message("User already exist. Email/Username/Phone number should be unique.").build());
        }
        User user = signinRequestMapper.toUser(signUpRequest);
        String userKey = UUID.randomUUID().toString();
        user.setUserKey(userKey);
        user.setPassword(passwordEncoder.encode(userKey + signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (CollectionUtils.isEmpty(strRoles)) {
            Role userRole = roleRepository.findByName(RoleConst.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                var r = switch (role.toLowerCase()) {
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
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok().body(
                SuccessResponse.builder().message("User registered successfully!").data(user.getUsername()).build());
    }

    public ResponseEntity<?> login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(User::isActive)
                .map(u -> {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    u.getUserKey() + request.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtService.generateJwtToken(authentication);
                    return ResponseEntity.ok().body(LoginResponse.builder().token(jwt).build());
                })
                .orElse(ResponseEntity
                        .badRequest()
                        .body(LoginResponse.builder()
                                .error("User not found or your account temporarily disabled. Please reach out to admin ")
                                .build()));

    }

    public void changePassword(ChangePasswordRequest request) {
       var user = userRepository.findByUsername(request.userName()).orElseThrow(() -> new InvalidRequestException("user not found"));
        var isMatchPassword = passwordEncoder.matches(user.getUserKey() + request.oldPassword(), user.getPassword());
        if (isMatchPassword) {
            var newUuid = UUID.randomUUID().toString();

            user.setUserKey(newUuid);
            user.setPassword(passwordEncoder.encode(newUuid + request.newPassword()));
            userRepository.save(user);
        } else {
            throw new InvalidRequestException("Invalid password");
        }

    }
}
