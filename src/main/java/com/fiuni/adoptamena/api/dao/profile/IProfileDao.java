package com.fiuni.adoptamena.api.dao.profile;

import com.fiuni.adoptamena.api.domain.profile.ProfileDomain;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface IProfileDao extends JpaRepository<ProfileDomain, Integer> {
    Optional<ProfileDomain> findByIdAndDeletedFalse(Integer id);
}
