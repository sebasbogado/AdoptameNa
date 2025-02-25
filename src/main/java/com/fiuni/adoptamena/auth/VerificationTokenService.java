package com.fiuni.adoptamena.auth;

import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.dao.user.IVerificationTokenDao;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    @Autowired
    private final IVerificationTokenDao verificationTokenDao;

    @Autowired
    private final IUserDao userDao;

    public String createVerificationToken(UserDomain user) {
        // Generar un nuevo token y guardarlo en la BD
        VerificationTokenDomain token = new VerificationTokenDomain(user);
        verificationTokenDao.save(token);
        return token.getToken();
    }

    public VerificationTokenDomain getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Token de verificación inválido o expirado."));
    }

    @Transactional
    public void deleteToken(UserDomain user) {
        VerificationTokenDomain token = verificationTokenDao.findByUser(user);
        if (token != null) {
            verificationTokenDao.delete(token);
        }
    }

    public AuthResponse verifyEmail(String token) {
        // Obtener el token de verificación
        VerificationTokenDomain verificationToken = getVerificationToken(token);

        //TODO: Verificar si el token ya expiro

        // Verificar si el token está asociado a un usuario
        UserDomain user = verificationToken.getUser();

        // Marcar al usuario como verificado
        user.setVerified(true);
        userDao.save(user);

        // Eliminar el token de la base de datos
        deleteToken(user);

        return AuthResponse.builder()
                .token("Cuenta verificada con éxito. Ahora puedes iniciar sesión.")
                .build();
    }
}
