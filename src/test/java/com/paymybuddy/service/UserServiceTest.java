package com.paymybuddy.service;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("steveLander")
                .password("12345")
                .email("steve.lander@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for saveUser method")
    @Test
    public void givenUserObject_whenSaveUser_thenReturnUserObject() {
        given(userRepository.findByUsernameEquals(user.getUsername()))
                .willReturn(Optional.empty());

        given(userRepository.save(user)).willReturn(user);

        System.out.println(userRepository);
        System.out.println(userService);

        User savedUser = userService.saveUser(user);

        System.out.println(savedUser);
        assertThat(savedUser).isNotNull();
    }

    @DisplayName("JUnit test for saveUser method which throws exception")
    @Test
    public void givenExistingUser_whenSaveUser_thenReturnNull() {
        given(userRepository.findByUsernameEquals(user.getUsername()))
                .willReturn(Optional.ofNullable(user));

        System.out.println(userRepository);
        System.out.println(userService);

        assertNull(userService.saveUser(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        User user1 = user;
        user1.setEmail("ram@gmail.com");
        user1.setUsername("Ram");

        given(userRepository.findById(user.getId())).willReturn(Optional.ofNullable(user));
        ResponseEntity<User> updatedUser = userService.updateUser(user1);

        assertThat(updatedUser.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void givenUserFirstNameAndLastName_whenDeleteUser_thenNothing() {
        given(userRepository.findByUsernameEquals(user.getUsername())).willReturn(Optional.ofNullable(user));
        willDoNothing().given(userRepository).delete(user);

        userService.deleteUser(user.getUsername());

        verify(userRepository, times(1)).delete(user);
    }
}
