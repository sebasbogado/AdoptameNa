package com.fiuni.adoptamena.auth.service;

import com.fiuni.adoptamena.api.dao.user.IVerificationTokenDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import com.fiuni.adoptamena.auth.response.GenericResponse;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;
import com.fiuni.adoptamena.exception_handler.exceptions.GoneException;
import com.fiuni.adoptamena.utils.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    private static final String BASE_VERIFICATION_LINK = "http://localhost:8080/auth/verify-email?token=";

    /**
     * Crea un nuevo token de verificación para un usuario y lo guarda en la base de
     * datos.
     * 
     * @param user Usuario para el cual se genera el token
     * @return Token de verificación generado
     */
    @Transactional
    public String createVerificationToken(UserDomain user) {
        VerificationTokenDomain token = new VerificationTokenDomain(user);
        verificationTokenDao.save(token);
        return token.getToken();
    }

    /**
     * Obtiene un token de verificación desde la base de datos.
     * 
     * @param token Token de verificación
     * @return Instancia de VerificationTokenDomain correspondiente al token
     */
    public VerificationTokenDomain getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Token de verificación inválido o expirado."));
    }

    /**
     * Elimina un token de verificación de la base de datos.
     * 
     * @param user Usuario asociado al token a eliminar
     */
    @Transactional
    public void deleteToken(UserDomain user) {
        VerificationTokenDomain token = verificationTokenDao.findByUser(user);
        if (token != null) {
            verificationTokenDao.delete(token);
        }
    }

    /**
     * Verifica el token de verificación y marca al usuario como verificado.
     * 
     * @param token Token de verificación
     * @return Respuesta con mensaje de éxito
     */
    @Transactional
    public GenericResponse verifyEmail(String token) {
        VerificationTokenDomain verificationToken = getVerificationToken(token);

        if (verificationToken.getExpiryDate().before(new Date())) {
            deleteToken(verificationToken.getUser());
            throw new GoneException("El token de verificación ha expirado. Solicita uno nuevo.");
        }

        UserDomain user = verificationToken.getUser();
        user.setIsVerified(true);

        verificationTokenDao.delete(verificationToken);

        return GenericResponse.builder()
                .message("Usuario verificado correctamente.")
                .build();
    }

    /**
     * Envía un correo de verificación a un usuario.
     * 
     * @param email Email del usuario
     * @return Respuesta con mensaje de éxito
     */
    public GenericResponse sendVerificationEmail(String email) {
        UserDomain user = userDao.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado."));

        deleteToken(user);

        String token = createVerificationToken(user);
        emailService.sendVerificationEmail(user.getEmail(), BASE_VERIFICATION_LINK + token);

        return GenericResponse.builder()
                .message("Correo de verificación enviado correctamente.")
                .build();
    }
}
