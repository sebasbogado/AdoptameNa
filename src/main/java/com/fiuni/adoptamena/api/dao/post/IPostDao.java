package com.fiuni.adoptamena.api.dao.post;

import com.fiuni.adoptamena.api.domain.post.PostDomain;
import org.springframework.data.repository.CrudRepository;

public interface IPostDao extends CrudRepository<PostDomain, Integer> {
}
