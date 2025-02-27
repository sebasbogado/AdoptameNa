package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.PostDomain;
import com.fiuni.adoptamena.api.dto.post.PostDTO;
import com.fiuni.adoptamena.api.service.base.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService extends IBaseService<PostDomain, PostDTO> {


    Page<PostDTO> getAllPosts(Pageable pageable, String title, String content, Integer userId, Integer postTypeId);
}
