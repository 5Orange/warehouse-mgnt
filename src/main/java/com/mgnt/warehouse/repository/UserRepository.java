package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameOrPhoneNumber(String username, String phoneNumber);

    Long removeUserById(String id);

    User findUserById(String id);
}
