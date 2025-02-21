package com.fiuni.adoptamena.api.service;

import com.fiuni.adoptamena.api.dao.postType.IPostTypeDao;
import com.fiuni.adoptamena.api.domain.postType.PostTypeDomain;
import com.fiuni.adoptamena.api.dto.PostTypeDto;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostTypeImpl implements IPostTypeService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeImpl.class);
    @Autowired
    IPostTypeDao postTypeDao;

    @Override
    public PostTypeDto save(PostTypeDto postTypeDto) {
        try {
            PostTypeDomain postTypeDomain =  convertDtoToDomain(postTypeDto);
            postTypeDao.save(postTypeDomain);
            log.info("PostType save successful");
        }catch (Exception e){
             new ErrorResponse("Error creating a Post Type", e.getMessage());
        }
        return postTypeDto;
    }

    @Override
    public PostTypeDto updateById(int id, PostTypeDto postTypeDto) {
        try {
            postTypeDto.setId(id);
        }
        catch (Exception e){
            new ErrorResponse("Error updating a Post Type", e.getMessage());
        }
        return save(postTypeDto);
    }

    @Override
    public void deleteById(int id) {

        try {
            PostTypeDomain postTypeDomain = postTypeDao.findById(id).orElse(null);
            if (postTypeDomain != null) {
                postTypeDao.delete(postTypeDomain);
            }
        } catch (Exception e){
            new ErrorResponse("Error deleting a Post Type", e.getMessage());
        }
    }


}
