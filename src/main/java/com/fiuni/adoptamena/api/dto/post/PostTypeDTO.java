package com.fiuni.adoptamena.api.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTypeDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio ")
    private String name;

    private String description;

}
