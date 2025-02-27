package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.RoleDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleDao extends CrudRepository<RoleDomain, Integer> {
    // findbyname
    Optional<RoleDomain> findByName(String name);
}
