package com.fiuni.adoptamena.auth.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fiuni.adoptamena.auth.response.AuthResponse;
import com.fiuni.adoptamena.auth.response.GenericResponse;
import com.fiuni.adoptamena.auth.response.LoginRequest;
import com.fiuni.adoptamena.auth.response.RegisterRequest;
import com.fiuni.adoptamena.auth.service.AuthService;
import com.fiuni.adoptamena.auth.service.VerificationTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @Autowired
    private final VerificationTokenService verificationTokenService;

    private final Boolean sendEmail = true;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid login request");
        }
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid registration request");
        }
        return ResponseEntity.ok(authService.register(request, sendEmail));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<GenericResponse> verifyEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(verificationTokenService.verifyEmail(token));
    }

    // Generar token nuevamente
    @PostMapping("/resend-verification-token")
    public ResponseEntity<GenericResponse> resendVerificationToken(@RequestParam("email") String email) {
        return ResponseEntity.ok(verificationTokenService.sendVerificationEmail(email));
    }

}