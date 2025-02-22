package com.fiuni.adoptamena.api.dao.post;

import com.fiuni.adoptamena.api.domain.post.PostDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IPostDao extends CrudRepository<PostDomain, Integer> {
    Page<PostDomain> findByFilters(Pageable pageable, String title, String content, Integer userId, Integer postTypeId);

    Page<PostDomain> findAll(Pageable pageable);
}
