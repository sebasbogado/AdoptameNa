package com.fiuni.adoptamena.api.dao.profile;

import com.fiuni.adoptamena.api.domain.profile.ProfileDomain;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface IProfileDao extends JpaRepository<ProfileDomain, Integer> {
    //Optional<ProfileDomain> findByEmail(String email);
    // Métodos adicionales si es necesario
    
}
