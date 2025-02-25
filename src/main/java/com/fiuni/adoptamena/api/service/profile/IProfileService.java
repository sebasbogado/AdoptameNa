package com.fiuni.adoptamena.api.service.profile;

import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
public interface IProfileService {
    //save
    ProfileDTO saveProfile(ProfileDTO profile, Integer userId);
    //update
    ProfileDTO updateProfile(ProfileDTO profile);
    //get by id
    ProfileDTO getById(Integer id);
    //delete
    void deleteProfile(Integer id);
}
