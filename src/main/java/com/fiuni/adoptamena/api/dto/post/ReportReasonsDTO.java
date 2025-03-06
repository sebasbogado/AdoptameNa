package com.fiuni.adoptamena.api.dto.post;

import com.fiuni.adoptamena.api.dto.base.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportReasonsDTO extends BaseDTO {

    @NotBlank(message = "La descripcion es obligatoria.")
    private String description;

}
