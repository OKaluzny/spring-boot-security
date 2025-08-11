package org.example.springbootsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginPageTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLoginPageAccessible() throws Exception {
        System.out.println("[DEBUG_LOG] Testing login page accessibility without circular view path error");

        // Test that GET /login returns the login page without circular view path error
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/login", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Login Form"));
        assertTrue(response.getBody().contains("/login/process"));

        System.out.println("[DEBUG_LOG] Login page is accessible and contains correct form action");
    }

    @Test
    public void testLoginPageContainsCorrectFormAction() throws Exception {
        System.out.println("[DEBUG_LOG] Testing that login form submits to correct URL");

        // Verify that the login form has the correct action URL
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/login", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("action=\"/login/process\""));

        System.out.println("[DEBUG_LOG] Login form contains correct action URL: /login/process");
    }
}
