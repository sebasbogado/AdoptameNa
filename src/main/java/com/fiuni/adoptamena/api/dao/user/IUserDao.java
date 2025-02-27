package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserDao extends JpaRepository<UserDomain, Integer> {
    Optional<UserDomain> findByEmail(String email);
    // Métodos adicionales si es necesario
    Optional<UserDomain> findByIdAndIsDeletedFalse(Integer id);
}
