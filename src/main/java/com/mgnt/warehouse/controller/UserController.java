package com.mgnt.warehouse.controller;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.repository.UserRepository;
import com.mgnt.warehouse.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
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
                .map(i -> ResponseEntity.ok(userRepository.findUserById(id)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUser(@RequestParam("id") String id) {
        return ofNullable(id)
                .map(i -> ResponseEntity.ok("User deleted: " + userRepository.removeUserById(id)))
                .orElse(new ResponseEntity<>("id must not be null", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user, String id) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}
