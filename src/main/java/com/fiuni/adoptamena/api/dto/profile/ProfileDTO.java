package com.fiuni.adoptamena.api.dto.profile;

import com.fiuni.adoptamena.api.domain.profile.EnumGender;
import com.fiuni.adoptamena.api.dto.base.BaseDTO;
import lombok.*;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Data
@EqualsAndHashCode(callSuper = false)
public class ProfileDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 100, message = "El nombre de la organización debe tener entre 1 y 100 caracteres")
    private String organizationName;

    @Size(min = 3, max = 100, message = "El nombre completo debe tener entre 1 y 100 caracteres")
    @NotBlank(message = "El nombre completo es obligatorio")
    private String fullName;

    @Size(max = 255, message = "El address debe tener entre 1 y 100 caracteres")
    private String address;

    @Size(max = 500, message = "La descripcion debe tener entre 1 y 100 caracteres")
    private String description;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "El genero debe ser MALE, FEMALE u OTHER")
    private EnumGender gender;

    private Date birthdate;

    @Size(max = 30, message = "El documento debe tener maximo 30 caracteres")
    private String document;

    @Size(max = 15, message = "El telefono debe tener maximo 15 caracteres")
    private String phoneNumber;

    private int earnedPoints;

    @Size(max = 255, message = "Las coodenadas deben tener maximo 255 caracteres")
    private String addressCoordinates;
}
