package com.fiuni.adoptamena.api.dto.base;

import java.io.Serial;
import java.io.Serializable;
import lombok.*;

@Data
public abstract class BaseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
}
