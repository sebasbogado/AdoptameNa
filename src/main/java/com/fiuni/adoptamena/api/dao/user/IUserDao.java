package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.UserDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserDao extends CrudRepository<UserDomain, Integer> {
    Optional<UserDomain> findByEmail(String email);
    // Métodos adicionales si es necesario
    Optional<UserDomain> findByIdAndIsDeletedFalse(Integer id);
}
