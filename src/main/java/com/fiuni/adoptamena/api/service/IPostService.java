package com.fiuni.adoptamena.api.service;

import com.fiuni.adoptamena.api.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService {
    PostDto save(PostDto postDto);

    PostDto updateById(int id, PostDto postDto);

    void deleteById(int id);

    PostDto getById(int id);

    Page<PostDto> getAllPosts(Pageable pageable, String title, String content, Integer userId, Integer postTypeId);
}
