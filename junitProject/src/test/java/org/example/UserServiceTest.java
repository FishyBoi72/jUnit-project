package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testRegisterUserSuccess() {
        User user = new User("JaneDoe", "password123", "janedoe@example.com");
        boolean result = userService.registerUser(user);
        assertTrue(result, "User registration should be successful");
    }

    @Test
    void testRegisterUserAlreadyExists() {
        User user = new User("JaneDoe", "password123", "janedoe@example.com");
        userService.registerUser(user);
        boolean result = userService.registerUser(user);
        assertFalse(result, "User registration should fail if the user already exists");
    }

    @Test
    void testRegisterUserWithEmptyUsername() {
        User user = new User("", "password123", "janedoe@example.com");
        boolean result = userService.registerUser(user);
        assertFalse(result, "User registration should fail with empty username");
    }

    @Test
    void testLoginUserSuccess() {
        User user = new User("JaneDoe", "password123", "janedoe@example.com");
        userService.registerUser(user);
        User loggedInUser = userService.loginUser("JaneDoe", "password123");
        assertNotNull(loggedInUser, "Login should be successful with correct username and password");
    }

    @Test
    void testLoginUserWithIncorrectPassword() {
        User user = new User("JaneDoe", "password123", "janedoe@example.com");
        userService.registerUser(user);
        User loggedInUser = userService.loginUser("JaneDoe", "wrongpassword");
        assertNull(loggedInUser, "Login should fail with incorrect password");
    }

    @Test
    void testLoginUserNotFound() {
        User loggedInUser = userService.loginUser("NonExistentUser", "password123");
        assertNull(loggedInUser, "Login should fail if the user is not found");
    }
}