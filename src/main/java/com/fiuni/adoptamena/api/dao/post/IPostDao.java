package com.fiuni.adoptamena.api.dao.post;

import com.fiuni.adoptamena.api.domain.post.PostDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IPostDao extends CrudRepository<PostDomain, Integer> {
    @Query("SELECT p FROM PostDomain p WHERE " +
            "(COALESCE(:title, '') = '' OR p.title LIKE %:title%) AND " +
            "(COALESCE(:content, '') = '' OR p.content LIKE %:content%) AND " +
            "(COALESCE(:userId, NULL) IS NULL OR p.user.id = :userId) AND " +
            "(COALESCE(:postTypeId, NULL) IS NULL OR p.postType.id = :postTypeId)")
    Page<PostDomain> findByFiltersAAndIsDeletedFalse(Pageable pageable, String title, String content, Integer userId, Integer postTypeId);

    Page<PostDomain> findAllByIsDeletedFalse(Pageable pageable);
}
