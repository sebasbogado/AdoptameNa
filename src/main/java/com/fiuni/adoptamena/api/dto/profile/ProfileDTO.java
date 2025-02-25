package com.fiuni.adoptamena.api.dto.profile;

import com.fiuni.adoptamena.api.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class ProfileDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String organizationName;
    private String name;
    private String lastName;
    private String address;
    private String description;
    private String gender;
    private Date birthdate;
    private String document;
    private String phoneNumber;
    private int earnedPoints;
    private Boolean isDeleted;
    private String addressCoordiantes;
}
