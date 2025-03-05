package com.fiuni.adoptamena.api.dto.post;

import com.fiuni.adoptamena.api.dto.base.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReportDto extends BaseDTO {

    @NotBlank(message = "El usuario debe ser Obligatorio.")
    private Integer idUser;

    @NotBlank(message = "El post debe ser Obligatorio.")
    private Integer idPost;

    @NotBlank(message = "La razon del reporte debe ser Obligatorio.")
    private Integer idReportReason;

    private String description;

    private Date reportDate;

    private String status;
}
