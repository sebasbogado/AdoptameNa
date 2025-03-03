package com.fiuni.adoptamena.api.dto.pet_status;

import com.fiuni.adoptamena.api.dto.base.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class PetStatusDTO extends BaseDTO{
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @NotBlank
    private String name;

    @Size(min = 3, max = 500, message = "La descripcion debe tener entre 3 y 500 caracteres")
    private String description;
}
