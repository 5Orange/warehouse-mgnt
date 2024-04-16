package com.mgnt.warehouse.service.impl;

import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.modal.auth.UserPrinciple;
import com.mgnt.warehouse.repository.UserRepository;
import com.mgnt.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u = Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        return UserPrinciple.build(u.get());
    }
}
