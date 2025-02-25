package com.fiuni.adoptamena.api.service.profile;

import com.fiuni.adoptamena.api.dao.profile.IProfileDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.profile.*;
import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
import com.fiuni.adoptamena.exception_handler.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
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
        ProfileDomain domain = profileDao.findById(id).orElseThrow(()-> 
            new ResourceNotFoundException("Perfil no encontrado"));
        return convertDomainToDTO(domain);
    }
    public void saveProfile(Integer userId) {
        log.info("Creando perfil {}");
        ProfileDomain domain = new ProfileDomain();
        domain.setUser(userDao.findById(userId).orElseThrow(()-> 
            new ResourceNotFoundException("Usuario no encontrado")));

        setDefaultAttributes(domain);
        log.info("Usuario a guardar, {}", domain);
        profileDao.save(domain);
        return;
    }
    //IN-PROGRESS
    public ProfileDTO updateProfile(ProfileDTO profile) {
        final ProfileDomain profileDomain = convertDtoToDomain(profile);
        final ProfileDomain profileDomainSaved = profileDao.save(profileDomain);
        return convertDomainToDTO(profileDomainSaved);
    }
    //delete when user is deleted

    private ProfileDomain setDefaultAttributes(ProfileDomain domain) {
        domain.setName(domain.getUser().getEmail());
        domain.setOrganizationName(null);
        domain.setLastName(null);
        domain.setAddress(null);
        domain.setDescription(null);
        domain.setBirthdate(null);
        domain.setGender(null);
        domain.setDocument(null);
        domain.setPhoneNumber(null);
        domain.setEarnedPoints(0);
        domain.setIsDeleted(false);
        domain.setAddressCoordinates(null);
        return domain;
    }
}
