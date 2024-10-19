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
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/list")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
        return ofNullable(id)
                .map(i -> ResponseEntity.ok(userRepository.findUserById(id)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUser(@RequestParam("id") Long id) {
        return ofNullable(id)
                .map(i -> ResponseEntity.ok("User deleted: " + userRepository.removeUserById(id)))
                .orElse(new ResponseEntity<>("id must not be null", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user, Long id) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}
