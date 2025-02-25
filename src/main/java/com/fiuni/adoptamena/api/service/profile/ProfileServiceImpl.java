package com.fiuni.adoptamena.api.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiuni.adoptamena.api.dao.profile.IProfileDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.profile.*;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements IProfileService {
    @Autowired 
    private IProfileDao profileDao;
    @Autowired 
    private IUserDao userDao;
    //dto to domain
    protected ProfileDomain convertDtoToDomain(ProfileDTO dto) {
        final ProfileDomain domain = new ProfileDomain();
        domain.setId(dto.getId());
        domain.setOrganizationName(dto.getOrganizationName());
        domain.setName(dto.getName());
        domain.setLastName(dto.getLastName());
        domain.setAddress(dto.getAddress());
        domain.setDescription(dto.getDescription());
        domain.setGender(dto.getGender());
        domain.setBirthdate(dto.getBirthdate());
        domain.setDocument(dto.getDocument());
        domain.setPhoneNumber(dto.getPhoneNumber());
        domain.setEarnedPoints(dto.getEarnedPoints());
        domain.setIsDeleted(dto.getIsDeleted());
        return domain;
    }
    //domain to dto
    protected ProfileDTO convertDomainToDTO(ProfileDomain domain) {
        final ProfileDTO dto = new ProfileDTO();
        dto.setId(domain.getId());
        dto.setOrganizationName(domain.getOrganizationName());
        dto.setName(domain.getName());
        dto.setLastName(domain.getLastName());
        dto.setAddress(domain.getAddress());
        dto.setDescription(domain.getDescription());
        dto.setGender(domain.getGender());
        dto.setBirthdate(domain.getBirthdate());
        dto.setDocument(domain.getDocument());
        dto.setPhoneNumber(domain.getPhoneNumber());
        dto.setEarnedPoints(domain.getEarnedPoints());
        dto.setIsDeleted(domain.getIsDeleted());
        return dto;
    }

    public ProfileDTO getById(Integer id) {
        return convertDomainToDTO(profileDao.findByIdAndDeletedFalse(id).orElse(null));
    }

    public ProfileDTO saveProfile(ProfileDTO profile, Integer userId) {
        log.info("ACA - Creando perfil {} ",profile);
        System.out.println("llega acaaaa");
        UserDomain user = userDao.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        profile.setId(userId);
        ProfileDomain profileDomain = convertDtoToDomain(profile);
        profileDomain.setUser(user); 

        ProfileDomain profileDomainSaved = profileDao.save(profileDomain);
        return convertDomainToDTO(profileDomainSaved);
    }

    public ProfileDTO updateProfile(ProfileDTO profile) {
        final ProfileDomain profileDomain = convertDtoToDomain(profile);
        final ProfileDomain profileDomainSaved = profileDao.save(profileDomain);
        return convertDomainToDTO(profileDomainSaved);
    }

    public void deleteProfile(Integer id) {
        profileDao.deleteById(id);
    }
}
