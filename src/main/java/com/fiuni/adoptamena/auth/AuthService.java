package com.fiuni.adoptamena.auth;

import com.fiuni.adoptamena.api.dao.user.IRoleDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;
import com.fiuni.adoptamena.jwt.JwtService;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.fiuni.adoptamena.utils.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("Email o contraseña incorrectos.", e);
        }

        UserDetails user = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con el email: " + request.getEmail()));

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Crear el usuario
        UserDomain user = new UserDomain();
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Obtener el rol
        user.setRole(roleDao.findByName("user")
                .orElseThrow(() -> new RuntimeException("Role not found")));

        user.setDeleted(false);
        user.setVerified(false);
        user.setCreationDate(new Date());

        // Guardar el usuario
        try {
            userDao.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Error al guardar usuario. Usuario ya existente1");
            throw new BadRequestException("Usuario ya existente");
        } catch (PersistenceException e) {
            log.error("Error al guardar usuario. Usuario ya existente2");

            throw new BadRequestException("Error de persistencia en la base de datos.");
        } catch (Exception e) {
            log.error("Error al guardar usuario. Usuario ya existente3");
            log.error(e.getMessage());
            throw new RuntimeException("Error al guardar usuario");
        }

        // Generar token de verificación
        String token = verificationTokenService.createVerificationToken(user);

        // Enviar email con enlace de verificación
        String verificationLink = "http://localhost:8080/auth/verify-email?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), verificationLink);

        // Responder con un mensaje de éxito
        return AuthResponse.builder()
                .token("Registro exitoso. Verifica tu email para activar la cuenta.")
                .build();
    }

    public AuthResponse verifyEmail(String token) {
        // Obtener el token de verificación
        VerificationTokenDomain verificationToken = verificationTokenService.getVerificationToken(token);

        // Verificar si el token está asociado a un usuario
        UserDomain user = verificationToken.getUser();

        // Marcar al usuario como verificado
        user.setVerified(true);
        userDao.save(user);

        // Eliminar el token de la base de datos
        verificationTokenService.deleteToken(user);

        return AuthResponse.builder()
                .token("Cuenta verificada con éxito. Ahora puedes iniciar sesión.")
                .build();
    }

}
