package com.fiuni.adoptamena.auth.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.fiuni.adoptamena.auth.AuthResponse;
import com.fiuni.adoptamena.auth.LoginRequest;
import com.fiuni.adoptamena.auth.RegisterRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8081")
class AuthControllerIntegrationTest {
    @BeforeEach
    public void setUp() throws InterruptedException {
        Thread.sleep(1000); // Espera 1 segundo
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void registerAndLoginFlow_Success() {
        // Arrange
        String email = "newuser@example.com";
        String password = "password123";
        RegisterRequest registerRequest = new RegisterRequest(email, password);

        // Act - Register
        ResponseEntity<AuthResponse> registerResponse = restTemplate.postForEntity(
                getBaseUrl() + "/auth/register",
                registerRequest,
                AuthResponse.class);

        // Assert - Register
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode(), "El registro debe ser exitoso");
        AuthResponse registerResponseBody = registerResponse.getBody();
        assertNotNull(registerResponseBody, "El cuerpo de la respuesta debe no ser nulo");
        assertNotNull(registerResponseBody.getToken(), "El registro debe devolver un token");

        // Act - Login
        LoginRequest loginRequest = new LoginRequest(email, password);
        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                getBaseUrl() + "/auth/login",
                loginRequest,
                AuthResponse.class);

        // Assert - Login
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode(), "El login debe ser exitoso");
        AuthResponse loginResponseBody = loginResponse.getBody();
        assertNotNull(loginResponseBody, "El cuerpo de la respuesta debe no ser nulo");
        assertNotNull(loginResponseBody.getToken(), "El login debe devolver un token");
    }

    @Test
    void register_WithExistingEmail_ShouldFail() {
        // Arrange
        String email = "existing@example.com";
        String password = "password123";
        RegisterRequest firstRegister = new RegisterRequest(email, password);
        RegisterRequest secondRegister = new RegisterRequest(email, "differentpassword");

        // Act - First registration
        restTemplate.postForEntity(
                getBaseUrl() + "/auth/register",
                firstRegister,
                AuthResponse.class);

        // Act - Second registration with same email
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                getBaseUrl() + "/auth/register",
                secondRegister,
                AuthResponse.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "Registration with existing email should fail");
    }

    @Test
    void login_WithInvalidCredentials_ShouldFail() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("nonexistent@example.com", "wrongpassword");

        // Act
        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                getBaseUrl() + "/auth/login",
                loginRequest,
                AuthResponse.class);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode(),
                "Login with invalid credentials should fail");
    }

    @Test
    void register_WithInvalidEmail_ShouldFail() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("invalidemail", "password123");

        // Act
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                getBaseUrl() + "/auth/register",
                registerRequest,
                AuthResponse.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "El registro con un email no válido debe fallar");
    }

    @Test
    void register_WithShortPassword_ShouldFail() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("valid@example.com", "123");

        // Act
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                getBaseUrl() + "/auth/register",
                registerRequest,
                AuthResponse.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "El registro con una contraseña corta debe fallar");
    }
}
