package com.fiuni.adoptamena.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Verifica tu cuenta");
            helper.setText("Por favor, haz clic en el siguiente enlace para verificar tu cuenta: " + verificationLink);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error al enviar el correo de verificación", e);
            throw new RuntimeException("No se pudo enviar el correo de verificación.");
        }
    }
}
