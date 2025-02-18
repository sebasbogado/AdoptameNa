package com.fiuni.adoptamena.api.domain.user;

import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import com.fiuni.adoptamena.api.domain.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain implements BaseDomain, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, unique = true)
    private Integer id;

    @Column(name = "str_username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "str_email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "str_password", nullable = false)
    private String password;

    @Column(name = "date_creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private RoleDomain role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @Column(name = "bool_deleted", nullable = false)
    private Boolean deleted;

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.getName())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
