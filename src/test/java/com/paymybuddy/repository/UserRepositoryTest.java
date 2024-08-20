package com.paymybuddy.repository;

import com.paymybuddy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("steveLander")
                .password("12345")
                .email("steve.lander@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for save user operation")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get user by id operation")
    @Test
    public void givenUserObject_whenFindById_thenReturnUserObject() {
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        assertThat(optionalUser.isPresent()).isTrue();
        User userDB = optionalUser.get();
        assertThat(userDB).isNotNull();
    }

    @DisplayName("JUnit test for get user by userName operation")
    @Test
    public void givenUserObject_whenFindByUserNameEquals_thenReturnUserObject() {
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findByUsernameEquals(user.getUsername());
        assertThat(optionalUser.isPresent()).isTrue();
        User userDB = optionalUser.get();
        assertThat(userDB).isNotNull();
    }

    @DisplayName("JUnit test for update user operation")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(user.getId());
        assertThat(optionalUser.isPresent()).isTrue();

        User savedUser = optionalUser.get();
        savedUser.setEmail("ram@gmail.com");
        savedUser.setUsername("Ram");
        User updatedUser =  userRepository.save(savedUser);

        assertThat(updatedUser.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedUser.getUsername()).isEqualTo("Ram");
    }

    @DisplayName("JUnit test for delete user operation")
    @Test
    public void givenUserObject_whenDelete_thenRemoveUser() {

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> optionalUser = userRepository.findById(user.getId());

        assertThat(optionalUser).isEmpty();
    }
}
