package com.fiuni.adoptamena.api.domain.user;
import com.fiuni.adoptamena.api.domain.profile.Profile;
import com.fiuni.adoptamena.api.domain.role.RoleDomain;
import lombok.*;
import jakarta.persistence.*;
import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDomain {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, unique = true)
    private Integer id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @Column(name = "str_username")
    private String username;

    @Column(name = "str_password")
    private String password;

    @Column(name="str_email")
    private String email;

    @Column(name = "date_creation_date")
    private Date creationDate;

    @Column(name="bool_deleted")
    private Boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<RoleDomain> roles = new HashSet<>();
}
