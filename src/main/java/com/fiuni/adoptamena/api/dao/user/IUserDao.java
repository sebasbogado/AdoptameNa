package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.UserDomain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserDao extends CrudRepository<UserDomain, Integer> {

    // Obtener un usuario por su id
    Optional<UserDomain> findByIdAndIsDeletedFalse(Integer id);

    // Obtener un usuario por su email
    Optional<UserDomain> findByEmailAndIsDeletedFalse(String email);

    Optional<UserDomain> findByEmail(String email);
}
