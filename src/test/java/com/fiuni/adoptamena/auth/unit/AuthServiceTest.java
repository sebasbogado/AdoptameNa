package com.fiuni.adoptamena.auth.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fiuni.adoptamena.api.dao.user.IRoleDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.user.RoleDomain;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.auth.AuthResponse;
import com.fiuni.adoptamena.auth.AuthService;
import com.fiuni.adoptamena.auth.LoginRequest;
import com.fiuni.adoptamena.auth.RegisterRequest;
import com.fiuni.adoptamena.jwt.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private IUserDao userDao;
    @Mock
    private IRoleDao roleDao;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginSuccess() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        UserDomain user = new UserDomain();
        user.setEmail("test@example.com");

        when(userDao.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginFailsWhenUserNotFound() {
        LoginRequest request = new LoginRequest("unknown@example.com", "password");

        when(userDao.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> authService.login(request));

        assertEquals("Usuario no encontrado con el email: " + request.getEmail(), exception.getMessage());
    }

    @Test
    void loginFailsWhenAuthenticationFails() {
        LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(BadCredentialsException.class, () -> authService.login(request));

        assertEquals("Email o contraseña incorrectos.", exception.getMessage());
    }

    @Test
    void registerSuccess() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password", "USER");
        RoleDomain userRole = new RoleDomain();
        userRole.setName("user");

        when(roleDao.findByName("user")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("token");
        when(userDao.save(any(UserDomain.class))).thenAnswer(i -> i.getArgument(0));

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("token", response.getToken());

        ArgumentCaptor<UserDomain> userCaptor = ArgumentCaptor.forClass(UserDomain.class);
        verify(userDao).save(userCaptor.capture());
        UserDomain savedUser = userCaptor.getValue();

        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(userRole, savedUser.getRole());
    }

    @Test
    void registerFailsWhenRoleNotFound() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password", "USER");

        when(roleDao.findByName("user")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(request));

        assertEquals("Role not found", exception.getMessage());
    }
}
