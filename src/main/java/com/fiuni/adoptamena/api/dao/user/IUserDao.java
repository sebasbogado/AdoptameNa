package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.UserDomain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Repository
public interface IUserDao extends CrudRepository<UserDomain, Integer> {

    // Obtener un usuario por su id
    Optional<UserDomain> findByIdAndIsDeletedFalse(Integer id);

    // Obtener un usuario por su email y que no haya sido eliminado
    Optional<UserDomain> findByEmailAndIsDeletedFalse(String email);

    // Obtener un usuario por su email
    Optional<UserDomain> findByEmail(String email);

    // Obtener todos los usuarios no eliminados
    Page<UserDomain> findAllByIsDeletedFalse(Pageable pageable);
}
