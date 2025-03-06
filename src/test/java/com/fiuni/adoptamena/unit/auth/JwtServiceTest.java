package com.fiuni.adoptamena.unit.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.fiuni.adoptamena.jwt.JwtService;

import io.jsonwebtoken.security.SignatureException;

@SpringBootTest
public
class JwtServiceTest {
    private JwtService jwtService;
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "EXPIRATION_DURATION", 3600); // 1 hour
    }

    @Test
    void generateAndValidateToken() {
        UserDetails userDetails = new User("test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        String token = jwtService.getToken(userDetails);

        assertNotNull(token, "Generated token should not be null");
        assertTrue(jwtService.isTokenValid(token, userDetails), "Token should be valid for the given user");
        assertEquals("test@example.com", jwtService.getUsernameFromToken(token), "Token username should match");
    }

    @Test
    void validateExpiredToken() throws InterruptedException {
        UserDetails userDetails = new User("test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        ReflectionTestUtils.setField(jwtService, "EXPIRATION_DURATION", 1);

        String token = jwtService.getToken(userDetails);

        Thread.sleep(2000);

        assertFalse(jwtService.isTokenValid(token, userDetails), "Token should be invalid after expiration");
    }

    @Test
    void validateManipulatedToken() {
        UserDetails userDetails = new User("test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        String token = jwtService.getToken(userDetails);
        String manipulatedToken = token + "manipulated";

        assertFalse(jwtService.isTokenValid(manipulatedToken, userDetails),
                "Manipulated token should be invalid");

        assertThrows(SignatureException.class, () -> {
            jwtService.getUsernameFromToken(manipulatedToken);
        }, "Should throw SignatureException for manipulated token");
    }

    @Test
    void validateTokenWithWrongUser() {
        UserDetails userDetails = new User("test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        UserDetails otherUserDetails = new User("different@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtService.getToken(userDetails);

        assertFalse(jwtService.isTokenValid(token, otherUserDetails), "Token should not be valid for a different user");
    }
}
