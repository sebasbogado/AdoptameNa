package com.fiuni.adoptamena.api.dto.profile;

import com.fiuni.adoptamena.api.domain.profile.EnumGender;
import com.fiuni.adoptamena.api.dto.base.BaseDTO;

import lombok.*;

import java.util.Date;
@Data
public class ProfileDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String organizationName;
    private String name;
    private String lastName;
    private String address;
    private String description;
    private EnumGender gender;
    private Date birthdate;
    private String document;
    private String phoneNumber;
    private int earnedPoints;
    private Boolean isDeleted;
    private String addressCoordinates;
}
