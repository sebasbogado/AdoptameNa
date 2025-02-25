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
public class ProfileDomain implements BaseDomain {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile", nullable = false, unique = true)
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_profile")
    private UserDomain user;

    @Column(name="str_organization_name")
    private String organizationName;

    @Column(name="str_name")
    private String name;

    @Column(name="str_last_name")
    private String lastName;

    @Column(name="str_address")
    private String address;

    @Column(name="str_description")
    private String description;

    @Column(name="str_gender")
    private String gender;

    @Column(name="date_birthdate")
    private Date birthdate;

    @Column(name="str_document")
    private String document;

    @Column(name="str_phone_number")
    private String phoneNumber;

    @Column(name="int_earned_points")
    private int earnedPoints;

    @Column(name = "bool_deleted")
    private Boolean isDeleted;

    @Column(name="str_address_coordinates")
    private String addressCoordinates;
}
