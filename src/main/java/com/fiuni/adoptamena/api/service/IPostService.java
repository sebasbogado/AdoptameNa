package com.fiuni.adoptamena.api.service;

import com.fiuni.adoptamena.api.dto.PostDto;

public interface IPostService {
    PostDto save(PostDto postDto);

    PostDto updateById(int id, PostDto postDto);

    void deleteById(int id);

    PostDto getById(int id);
}
