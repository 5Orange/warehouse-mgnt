package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.UserDto;
import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.modal.mapper.UserMapper;
import com.mgnt.warehouse.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public UserDto updateUser(String id, User user) {
        String userId = ofNullable(id)
            .orElseGet(user::getId);
        return ofNullable(userId)
            .flatMap(x -> userRepository.findUserById(x)
                .map(u -> {
                    u.setPhoneNumber(user.getPhoneNumber());
                    u.setIndividualCard(user.getIndividualCard());
                    u.setEmail(user.getEmail());
                    u.setFullName(user.getFullName());
                    u.setAddress(user.getAddress());
                    return userRepository.save(u);
                })
                .map(userMapper::toDto)
            ).orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    public UserDto findUserById(String id) {
        return ofNullable(id)
            .flatMap(userRepository::findUserById)
            .map(userMapper::toDto)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    public UserDto findUserByToken(HttpServletRequest request) {
        return ofNullable(jwtService.getJwt(request))
            .filter(jwtService::validateJwtToken)
            .map(jwtService::getUserNameFromJwtToken)
            .flatMap(userRepository::findByUsername)
            .map(userMapper::toDto)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    public void inactiveUser(String id) {
        var user = ofNullable(id)
            .flatMap(userRepository::findUserById)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

}
