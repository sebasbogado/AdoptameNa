package com.fiuni.adoptamena.api.service.profile;

import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
public interface IProfileService {
    //save
    void saveProfile(Integer userId);
    //update
    ProfileDTO updateProfile(Integer id, ProfileDTO profile);
    //get by id
    ProfileDTO getById(Integer id);
}
