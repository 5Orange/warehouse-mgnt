package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.auth.Role;
import com.mgnt.warehouse.modal.auth.RoleConst;
import com.mgnt.warehouse.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitDumpData {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        roleRepository.findByName(RoleConst.ROLE_USER)
            .ifPresentOrElse((value) -> log.info("OK!"),
                () -> roleRepository.save(Role.builder().name(RoleConst.ROLE_USER).build()));

        roleRepository.findByName(RoleConst.ROLE_ADMIN)
            .ifPresentOrElse((value) -> log.info("OK!"),
                () -> roleRepository.save(Role.builder().name(RoleConst.ROLE_ADMIN).build()));

    }
}
