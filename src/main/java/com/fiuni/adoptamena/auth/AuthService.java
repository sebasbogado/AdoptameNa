package com.fiuni.adoptamena.auth;

import com.fiuni.adoptamena.api.dao.user.IRoleDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
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
        // Validar el role
        if (!("USER".equalsIgnoreCase(request.getRole()) || "ORGANIZATION".equalsIgnoreCase(request.getRole()))) {
            throw new BadRequestException("Rol inválido. Debe ser 'USER' o 'ORGANIZATION'");
        }

        // Crear el usuario
        UserDomain user = new UserDomain();
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Obtener el rol
        user.setRole(roleDao.findByName(request.getRole().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRole())));

        user.setDeleted(false);
        user.setCreationDate(new Date());

        // Guardar el usuario
        try {
            userDao.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Error al guardar usuario. Usuario ya existente", e);
            throw new BadRequestException("Usuario ya existente");
        } catch (PersistenceException e) {
            log.error("Error de persistencia en la base de datos", e);
            throw new BadRequestException("Error de persistencia en la base de datos.");
        } catch (Exception e) {
            log.error("Error al guardar usuario", e);
            throw new RuntimeException("Error al guardar usuario");
        }

        // Crear la respuesta
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtService.getToken(user));
        return authResponse;
    }

}
