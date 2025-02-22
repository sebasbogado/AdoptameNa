package com.fiuni.adoptamena.api.service;

import com.fiuni.adoptamena.api.dto.PostTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostTypeService {
    PostTypeDto save(PostTypeDto postTypeDto);

    PostTypeDto updateById(int id, PostTypeDto postTypeDto);

    void deleteById(int id);

    PostTypeDto getById(int id);

    Page<PostTypeDto> getAllPostTypes(Pageable pageable, String name, String description);
}
