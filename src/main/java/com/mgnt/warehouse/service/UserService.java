package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.BaseEntity;
import com.mgnt.warehouse.modal.auth.Role;
import com.mgnt.warehouse.modal.auth.RoleConst;
import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.repository.RoleRepository;
import com.mgnt.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public User updateUser(Long id, User user) {
        var userDb = ofNullable(id)
                .map(userRepository::findUserById)
                .orElseGet(() -> ofNullable(user)
                        .map(BaseEntity::getId)
                        .map(userRepository::findUserById)
                        .orElse(null)
                );
        return ofNullable(userDb)
                .map(u -> {
                    u.setPhoneNumber(user.getPhoneNumber());
                    u.setIndividualCard(user.getIndividualCard());
                    u.setEmail(user.getEmail());
                    u.setFullName(user.getFullName());
                    u.setAddress(user.getAddress());
                    return userRepository.save(u);
                }).orElse(null);


    }

    public Optional<Role> getRoleByName(RoleConst name) {
        return roleRepository.findByName(name);
    }
}
