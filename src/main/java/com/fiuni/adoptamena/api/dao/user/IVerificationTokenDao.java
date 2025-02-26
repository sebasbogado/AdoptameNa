package com.fiuni.adoptamena.api.dao.user;

import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.domain.user.VerificationTokenDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IVerificationTokenDao extends CrudRepository<VerificationTokenDomain, Integer> {
    Optional<VerificationTokenDomain> findByToken(String token);

    // FindByUser
    VerificationTokenDomain findByUser(UserDomain user);
}
