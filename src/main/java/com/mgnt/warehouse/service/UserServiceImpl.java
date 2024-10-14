package com.mgnt.warehouse.service;

import static java.util.Optional.ofNullable;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mgnt.warehouse.modal.auth.UserPrinciple;
import com.mgnt.warehouse.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ofNullable(username)
                .flatMap(u -> userRepository.findByUsername(u)
                        .map(UserPrinciple::build))
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

    }
}
