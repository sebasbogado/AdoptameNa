package com.fiuni.adoptamena.api.domain.pet;

import java.io.Serial;

import com.fiuni.adoptamena.api.domain.animal.AnimalDomain;
import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import com.fiuni.adoptamena.api.domain.health_state.HealthStateDomain;
import com.fiuni.adoptamena.api.domain.pet_status.PetStatusDomain;
import com.fiuni.adoptamena.api.domain.profile.EnumGender;
import com.fiuni.adoptamena.api.domain.race.RaceDomain;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDomain implements BaseDomain {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet", nullable = false, unique = true)
    private Integer id;

    @Column(name = "str_name", length = 100)
    private String name;

    @Column(name = "bool_vaccinated")
    private Boolean isVaccinated;

    @Column(name = "str_description", length = 500)
    private String description;

    @Column(name = "date_birthdate")
    private Date birthdate;

    @Column(name = "str_gender")
    private EnumGender gender;

    @Column(name = "str_url_photo")
    private String urlPhoto;

    @Column(name = "bool_sterilized")
    private Boolean isSterilized;

    @Column(name = "bool_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user", nullable = false)
    private UserDomain user;

    @ManyToOne
    @JoinColumn(name = "fk_id_animal",referencedColumnName = "id_animal" ,nullable = false)
    private AnimalDomain animal;

    @ManyToOne
    @JoinColumn(name = "fk_id_race", referencedColumnName = "id_race" ,nullable = false)
    private RaceDomain race;

    @ManyToOne
    @JoinColumn(name = "fk_id_health_state",referencedColumnName = "id_health_state" , nullable = false)
    private HealthStateDomain healthState;

    @ManyToOne
    @JoinColumn(name = "fk_id_pet_status", referencedColumnName = "id_pet_status" ,nullable = false)
    private PetStatusDomain petStatus;

}
