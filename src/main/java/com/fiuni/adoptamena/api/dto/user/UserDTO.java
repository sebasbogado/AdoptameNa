package com.fiuni.adoptamena.api.dto.user;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    private Integer id;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es válido")
    private String email;
    private Date creationDate;
    private String role;
    private Boolean isVerified;

}
