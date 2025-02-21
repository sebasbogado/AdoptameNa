package com.fiuni.adoptamena.api.dto;

import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTypeDto implements BaseDto {


    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;
}
