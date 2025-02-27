package com.fiuni.adoptamena.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email incorrecto")
    String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
    String password;

    @NotBlank(message = "El rol no puede estar vacío")
    private String role;
}
