package com.fiuni.adoptamena.api.service.profile;

import com.fiuni.adoptamena.api.dao.profile.IProfileDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.profile.*;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl extends BaseServiceImpl<ProfileDomain, ProfileDTO> implements IProfileService {
    @Autowired 
    private IProfileDao profileDao;
    @Autowired 
    private IUserDao userDao;
    //dto to domain
    @Override
    protected ProfileDomain convertDtoToDomain(ProfileDTO dto) {
        final ProfileDomain domain = new ProfileDomain();
        domain.setId(dto.getId());
        domain.setOrganizationName(dto.getOrganizationName());
        domain.setFullName(dto.getFullName());
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
    @Override
    protected ProfileDTO convertDomainToDto(ProfileDomain domain) {
        final ProfileDTO dto = new ProfileDTO();
        dto.setId(domain.getId());
        dto.setOrganizationName(domain.getOrganizationName());
        dto.setFullName(domain.getFullName());
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
    @Override
    public ProfileDTO getById(int id) {
        ProfileDomain domain = profileDao.findByIdAndIsDeletedFalse(id).orElseThrow(()-> 
            new ResourceNotFoundException("Perfil no encontrado"));
        return convertDomainToDto(domain);
    }
    //don't use
    @Override
    public ProfileDTO save(ProfileDTO profile) {
        return profile;
    }

    @Override
    public void save(int userId) {
        log.info("Creando perfil {}");
        ProfileDomain domain = new ProfileDomain();
        domain.setUser(userDao.findByIdAndIsDeletedFalse(userId).orElseThrow(()-> 
            new ResourceNotFoundException("Usuario no encontrado")));

        setDefaultAttributes(domain);
        log.info("Usuario a guardar, {}", domain);
        profileDao.save(domain);
        return;
    }
    @Override
    public ProfileDTO updateById(int id, ProfileDTO profile) {
        //validate user exists
        ProfileDomain existingProfile = profileDao.findByIdAndIsDeletedFalse(id).orElseThrow(()-> 
            new ResourceNotFoundException("Perfil no encontrado"));
        validateGender(profile.getGender().name());
        //validate user exists
        ProfileDomain profileDomain = convertDtoToDomain(profile);

        profileDomain.setId(id);
        ProfileDomain profileDomainSaved = profileDao.save(profileDomain);
        return convertDomainToDto(profileDomainSaved);
    }
    //delete when user is deleted in user service
    @Override
    public void deleteById(int id) {
        ProfileDomain domain = profileDao.findByIdAndIsDeletedFalse(id).orElseThrow(()-> 
        new ResourceNotFoundException("Perfil no encontrado"));
        
        //delete profile
        domain.setIsDeleted(true);
        
        UserDomain user = userDao.findByIdAndIsDeletedFalse(domain.getUser().getId()).orElseThrow(()-> 
            new ResourceNotFoundException("Usuario no encontrado"));
        //delete user
        user.setIsDeleted(true);
        //save
        userDao.save(user);
        profileDao.save(domain);
        log.info("Perfil eliminado {}", domain);
    }

    private ProfileDomain setDefaultAttributes(ProfileDomain domain) {
        domain.setFullName(domain.getUser().getEmail());
        domain.setOrganizationName(null);
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
    private void validateGender(String gender) {
        if(gender == null) {
            return;
        }
        try {
            EnumGender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(gender + " no es un género válido");    
        }
    }
}
