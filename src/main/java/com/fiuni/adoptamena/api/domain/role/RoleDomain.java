package com.fiuni.adoptamena.api.domain.role;

import com.fiuni.adoptamena.api.domain.user.User;
import lombok.*;
import jakarta.persistence.*;
import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDomain extends BaseDomain {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false, unique = true)
    private Integer id;

    @Column(name = "str_name")
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();
}
