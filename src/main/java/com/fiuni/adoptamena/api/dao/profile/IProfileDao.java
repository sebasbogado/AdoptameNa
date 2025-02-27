package com.fiuni.adoptamena.api.dao.profile;

import com.fiuni.adoptamena.api.domain.profile.ProfileDomain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface IProfileDao extends CrudRepository<ProfileDomain, Integer> {
    Optional<ProfileDomain> findByIdAndIsDeletedFalse(Integer id);
}
