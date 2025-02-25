package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVerificationTokenDao extends JpaRepository<VerificationTokenDomain, Integer> {
    Optional<VerificationTokenDomain> findByToken(String token);

    // FindByUser
    VerificationTokenDomain findByUser(UserDomain user);
}
