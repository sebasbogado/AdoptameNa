package com.fiuni.adoptamena.api.service;

import com.fiuni.adoptamena.api.dto.PostTypeDto;

public interface IPostTypeService {
    PostTypeDto save(PostTypeDto postTypeDto);

    PostTypeDto updateById(int id, PostTypeDto postTypeDto);

    void deleteById(int id);
}
