package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User updateUser(String id, User user) {
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
                ).orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    public User findUserById(String id) {
        return ofNullable(id)
                .flatMap(userRepository::findUserById)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    public void inactiveUser(String id) {
        var user = this.findUserById(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

}
