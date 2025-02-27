package com.fiuni.adoptamena.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiuni.adoptamena.api.domain.profile.ProfileDomain;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.exception_handler.exceptions.ResourceNotFoundException;
import com.fiuni.adoptamena.api.dao.profile.IProfileDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IProfileDao profileDao;

    @Override
    public void deleteUser(Integer id) {
        UserDomain domain = userDao.findByIdAndIsDeletedFalse(id).orElseThrow(()-> 
            new ResourceNotFoundException("Usuario no encontrado"));
        domain.setIsDeleted(true);
        //delete profile
        ProfileDomain profile = profileDao.findByIdAndIsDeletedFalse(id).orElseThrow(()-> 
            new ResourceNotFoundException("Perfil no encontrado"));
        profile.setIsDeleted(true);
        
        profileDao.save(profile);
        userDao.save(domain);
        log.info("Usuario eliminado: {}", domain);
    }
    
}
