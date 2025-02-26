package com.fiuni.adoptamena.auth;

import com.fiuni.adoptamena.api.dao.user.IVerificationTokenDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;
import com.fiuni.adoptamena.exception_handler.exceptions.GoneException;
import com.fiuni.adoptamena.utils.EmailService;

import java.util.Date;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    @Autowired
    private final IVerificationTokenDao verificationTokenDao;

    @Autowired
    private final IUserDao userDao;

    @Autowired
    private final EmailService emailService;

    // Crear un nuevo token de verificación y guardarlo en la base de datos
    @Transactional
    public String createVerificationToken(UserDomain user) {
        // Generar un nuevo token y guardarlo en la BD
        VerificationTokenDomain token = new VerificationTokenDomain(user);
        verificationTokenDao.save(token);
        return token.getToken();
    }

    // Obtener el token de verificación desde la base de datos
    public VerificationTokenDomain getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Token de verificación inválido o expirado."));
    }

    // Eliminar el token de verificación de la base de datos
    @Transactional
    public void deleteToken(UserDomain user) {
        VerificationTokenDomain token = verificationTokenDao.findByUser(user);
        if (token != null) {
            verificationTokenDao.delete(token);
        }
    }

    // Verificar el token de verificación y marcar al usuario como verificado
    @Transactional
    public AuthResponse verifyEmail(String token) {
        // Obtener el token de verificación desde la base de datos
        VerificationTokenDomain verificationToken = getVerificationToken(token);

        // Verificar si el token existe
        if (verificationToken == null) {
            throw new GoneException("El token de verificación no existe o ya ha sido utilizado.");
        }
        // Verificar si el token ha expirado
        if (verificationToken.getExpiryDate().before(new Date())) {
            deleteToken(verificationToken.getUser());
            throw new GoneException("El token de verificación ha expirado. Solicita uno nuevo.");
        }

        // Obtener el usuario asociado al token
        UserDomain user = verificationToken.getUser();

        // Marcar el usuario como verificado
        user.setIsVerified(true);

        // Eliminar el token de la base de datos para evitar reutilización
        verificationTokenDao.delete(verificationToken);

        // Retornar respuesta exitosa
        return new AuthResponse("Email verificado correctamente.");
    }

    // Eliminar token de verificación y enviar uno nuevo al usuario
    public AuthResponse resendVerificationToken(String email) {
        log.info("Iniciando proceso de reenvío de token para el email: {}", email);

        // Verificar si el usuario existe y no está eliminado
        UserDomain user = userDao.findByEmail(email)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> {
                    log.warn("Intento de reenvío de token a un usuario inexistente o eliminado: {}", email);
                    return new BadRequestException("El usuario no existe.");
                });

        // Verificar si el usuario ya está verificado
        if (user.getIsVerified()) {
            log.warn("El usuario {} ya está verificado. No se enviará un nuevo token.", email);
            throw new BadRequestException("El usuario ya está verificado.");
        }

        log.info("Eliminando token de verificación anterior para el usuario: {}", email);
        deleteToken(user);

        log.info("Generando nuevo token de verificación para el usuario: {}", email);
        String newToken = createVerificationToken(user);

        log.info("Enviando nuevo token de verificación al email: {}", email);
        emailService.sendVerificationEmail(newToken, user.getEmail());

        log.info("Token reenviado correctamente al usuario: {}", email);
        return new AuthResponse("Token de verificación reenviado correctamente.");
    }

}
