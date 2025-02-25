package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.PostTypeDomain;
import com.fiuni.adoptamena.api.dto.PostTypeDto;
import com.fiuni.adoptamena.api.service.base.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostTypeService extends IBaseService<PostTypeDomain, PostTypeDto> {

    Page<PostTypeDto> getAllPostTypes(Pageable pageable, String name, String description);
}
