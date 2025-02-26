package com.fiuni.adoptamena.auth;

import com.fiuni.adoptamena.api.dao.user.IVerificationTokenDao;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;
import com.fiuni.adoptamena.exception_handler.exceptions.GoneException;
import java.util.Date;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    @Autowired
    private final IVerificationTokenDao verificationTokenDao;

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
            throw new GoneException("El token de verificación ha expirado. Solicita uno nuevo.");
        }

        // Obtener el usuario asociado al token
        UserDomain user = verificationToken.getUser();

        // Marcar el usuario como verificado
        user.setVerified(true);

        // Eliminar el token de la base de datos para evitar reutilización
        verificationTokenDao.delete(verificationToken);

        // Retornar respuesta exitosa
        return new AuthResponse("Email verificado correctamente.");
    }

}
