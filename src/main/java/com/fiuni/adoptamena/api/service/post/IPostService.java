package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.PostDomain;
import com.fiuni.adoptamena.api.dto.PostDto;
import com.fiuni.adoptamena.api.service.base.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService extends IBaseService<PostDomain, PostDto> {


    Page<PostDto> getAllPosts(Pageable pageable, String title, String content, Integer userId, Integer postTypeId);
}
