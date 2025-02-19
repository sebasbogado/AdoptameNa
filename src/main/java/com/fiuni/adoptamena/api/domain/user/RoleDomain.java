package com.fiuni.adoptamena.api.domain.user;

import lombok.*;
import jakarta.persistence.*;
import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import java.io.Serial;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDomain implements BaseDomain {

    @Serial
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false, unique = true)
    private Integer id;

    @Column(name = "str_name", nullable = false, length = 50, unique = true)
    private String name;

}
