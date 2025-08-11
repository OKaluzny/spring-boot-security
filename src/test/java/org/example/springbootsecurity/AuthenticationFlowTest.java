package org.example.springbootsecurity;

import org.example.springbootsecurity.dto.UserDto;
import org.example.springbootsecurity.entity.User;
import org.example.springbootsecurity.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class AuthenticationFlowTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserRegistrationWithCorrectRole() {
        System.out.println("[DEBUG_LOG] Testing user registration with correct role assignment");
        
        // Create a test user
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@test.com");
        userDto.setPassword("password123");

        // Save the user
        userService.saveUser(userDto);
        System.out.println("[DEBUG_LOG] User saved successfully");

        // Retrieve the user
        User savedUser = userService.findByEmail("john.doe@test.com");
        assertNotNull(savedUser, "User should be saved and retrievable");
        System.out.println("[DEBUG_LOG] User retrieved: " + savedUser.getEmail());

        // Verify user details
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john.doe@test.com", savedUser.getEmail());
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword()));
        System.out.println("[DEBUG_LOG] User details verified");

        // Verify role assignment - should be ROLE_USER, not ROLE_ADMIN
        assertNotNull(savedUser.getRoles());
        assertFalse(savedUser.getRoles().isEmpty());
        assertEquals("ROLE_USER", savedUser.getRoles().get(0).getName());
        System.out.println("[DEBUG_LOG] User role verified: " + savedUser.getRoles().get(0).getName());
    }

    @Test
    public void testUserListConversion() {
        System.out.println("[DEBUG_LOG] Testing user list conversion");
        
        // Create and save a test user
        UserDto userDto = new UserDto();
        userDto.setFirstName("Jane");
        userDto.setLastName("Smith");
        userDto.setEmail("jane.smith@test.com");
        userDto.setPassword("password456");

        userService.saveUser(userDto);
        System.out.println("[DEBUG_LOG] Test user created");

        // Test the findAllUsers method (which uses convertEntityToDto)
        var users = userService.findAllUsers();
        assertFalse(users.isEmpty());
        
        // Find our test user
        var testUser = users.stream()
                .filter(u -> "jane.smith@test.com".equals(u.getEmail()))
                .findFirst()
                .orElse(null);
        
        assertNotNull(testUser);
        assertEquals("Jane", testUser.getFirstName());
        assertEquals("Smith", testUser.getLastName());
        assertEquals("jane.smith@test.com", testUser.getEmail());
        System.out.println("[DEBUG_LOG] User list conversion works correctly");
    }

    @Test
    public void testNameSplittingEdgeCase() {
        System.out.println("[DEBUG_LOG] Testing name splitting edge case");
        
        // Create a user with a single name (edge case)
        UserDto userDto = new UserDto();
        userDto.setFirstName("Madonna");
        userDto.setLastName(""); // Empty last name
        userDto.setEmail("madonna@test.com");
        userDto.setPassword("password789");

        userService.saveUser(userDto);
        System.out.println("[DEBUG_LOG] User with single name saved");

        // Test the conversion
        var users = userService.findAllUsers();
        var testUser = users.stream()
                .filter(u -> "madonna@test.com".equals(u.getEmail()))
                .findFirst()
                .orElse(null);
        
        assertNotNull(testUser);
        assertEquals("Madonna", testUser.getFirstName());
        assertEquals("", testUser.getLastName()); // Should handle empty last name gracefully
        System.out.println("[DEBUG_LOG] Name splitting edge case handled correctly");
    }
}