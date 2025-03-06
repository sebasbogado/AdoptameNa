package com.fiuni.adoptamena.api.dao.profile;

import com.fiuni.adoptamena.api.domain.profile.ProfileDomain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
@Repository
public interface IProfileDao extends CrudRepository<ProfileDomain, Integer> {
    Optional<ProfileDomain> findByIdAndIsDeletedFalse(Integer id);
    Page<ProfileDomain> findAllByIsDeletedFalse(Pageable pageable);
}
