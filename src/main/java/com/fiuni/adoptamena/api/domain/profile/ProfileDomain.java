package com.fiuni.adoptamena.api.domain.profile;

import lombok.*;
import jakarta.persistence.*;
import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import java.io.Serial;
import java.util.Date;

import com.fiuni.adoptamena.api.domain.user.UserDomain;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileDomain implements BaseDomain {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Asegura que el campo "id" es tratado como la PK de esta entidad
    @Column(name = "id_profile", nullable = false, unique = true)
    @MapsId // Utilizamos MapsId para que Profile comparta el mismo ID de User
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_profile", referencedColumnName = "id_user")
    private UserDomain user;

    @Column(name = "str_organization_name", length = 100)
    private String organizationName;

    @Column(name = "str_full_name", length = 100)
    private String fullName;

    @Column(name = "str_address", length = 255)
    private String address;

    @Column(name = "str_description", length = 500)
    private String description;

    @Column(name = "str_gender")
    private EnumGender gender;

    @Column(name = "date_birthdate")
    private Date birthdate;

    @Column(name = "str_document", length = 30)
    private String document;

    @Column(name = "str_phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "int_earned_points")
    private int earnedPoints;

    @Column(name = "bool_deleted")
    private Boolean isDeleted;

    @Column(name = "str_address_coordinates", length = 255)
    private String addressCoordinates;
}
