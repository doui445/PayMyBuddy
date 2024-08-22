package com.paymybuddy.controller;

import com.paymybuddy.model.User;
import com.paymybuddy.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("steveLander")
                .password("12345")
                .email("steve.lander@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for createUser method")
    @Test
    public void givenUserObject_whenCreateUser_thenReturnUserObject() {
        given(userService.saveUser(user))
                .willReturn(user);
        User savedUser = userController.createUser(user);
        assertThat(savedUser).isEqualTo(user);
    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnResponseEntityOfUser() {
        given(userService.updateUser(user))
                .willReturn(new ResponseEntity<>(user, HttpStatus.OK));
        ResponseEntity<User> updatedUser = userController.updateUser(user);
        assertThat(updatedUser.getBody()).isEqualTo(user);
        assertThat(updatedUser.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void givenUserObject_whenDeleteUser_thenReturnResponseEntityOK() {
        doNothing().when(userService).deleteUser(user.getUsername());
        ResponseEntity<String> isDeleted = userController.deleteUser(user.getUsername());
        assertThat(isDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
