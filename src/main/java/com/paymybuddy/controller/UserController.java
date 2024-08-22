package com.paymybuddy.controller;

import com.paymybuddy.model.User;
import com.paymybuddy.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserController {
    private UserServiceImpl userService;

    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User";
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body("User " + username + " deleted successfully!");
    }
}
