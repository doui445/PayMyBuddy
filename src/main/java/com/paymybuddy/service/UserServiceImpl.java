package com.paymybuddy.service;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        Optional<User> savedUser = userRepository.findByUsernameEquals(user.getUsername());
        if(savedUser.isPresent()){
            System.out.println("User already exist with given email:" + savedUser.get().getEmail());
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<User> updateUser(User user) {
        return userRepository.findById(user.getId())
                .map(savedUser -> {
                    savedUser.setUsername(user.getUsername());
                    savedUser.setPassword(user.getPassword());
                    savedUser.setEmail(user.getEmail());
                    savedUser.setConnections(user.getConnections());

                    User updatedUser = userRepository.save(savedUser);
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void deleteUser(String username) {
        userRepository.findByUsernameEquals(username)
                .ifPresent(user -> userRepository.delete(user));
    }
}
