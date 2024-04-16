package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.auth.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    UserDetails getUserByUsername(String username);
}
