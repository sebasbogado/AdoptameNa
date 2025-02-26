package com.fiuni.adoptamena.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportReasonsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String description;

    private Boolean isDeleted;
}
