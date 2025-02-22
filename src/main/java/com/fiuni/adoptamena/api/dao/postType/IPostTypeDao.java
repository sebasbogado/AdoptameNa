package com.fiuni.adoptamena.api.dao.postType;

import com.fiuni.adoptamena.api.domain.postType.PostTypeDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostTypeDao extends CrudRepository<PostTypeDomain, Integer> {
    Page<PostTypeDomain> findAll(Pageable pageable);

    Page<PostTypeDomain> findByNameContainingAndDescriptionContaining(String name, String description, Pageable pageable);
}
