package com.paymybuddy.service;

import com.paymybuddy.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User saveUser(User user);
    ResponseEntity<User> updateUser(User user);
    void deleteUser(String username);
}
