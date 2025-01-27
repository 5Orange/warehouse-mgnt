package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.repository.UserRepository;
import com.mgnt.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/list")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable String id) {
        return ofNullable(id)
                .map(i -> ResponseEntity.ok(userService.findUserById(id)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeUser(@PathVariable("id") String id) {
        return ofNullable(id)
                .map(i -> {
                    userService.inactiveUser(id);
                    return ResponseEntity.accepted().body("Received");
                })
                .orElse(new ResponseEntity<>("id must not be null", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user, String id) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}
