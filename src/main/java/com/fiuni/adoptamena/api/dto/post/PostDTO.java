package com.fiuni.adoptamena.api.dto.post;

import com.fiuni.adoptamena.api.dto.base.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostDTO extends BaseDTO {

    @NotBlank(message = "El usuario debe ser Obligatorio.")
    private Integer idUser;

    @NotBlank(message = "El titulo es Obligatorio.")
    @Size(min =5, max= 255, message = "El titulo debe tener como minimo 5 caracteres.")
    private String title;

    private String content;

    @NotBlank(message = "El tipo de post debe ser Obligatorio.")
    private Integer idPostType;

    private String locationCoordinates;

    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numbers")
    private String contactNumber;

    private String status;

    private int sharedCounter;

    private Date publicationDate;

}
