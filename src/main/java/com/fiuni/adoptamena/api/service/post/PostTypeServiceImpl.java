package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.dao.post.IPostTypeDao;
import com.fiuni.adoptamena.api.domain.post.PostTypeDomain;
import com.fiuni.adoptamena.api.dto.PostTypeDto;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostTypeServiceImpl extends BaseServiceImpl<PostTypeDomain, PostTypeDto> implements IPostTypeService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);
    @Autowired
    IPostTypeDao postTypeDao;

    @Override
    public PostTypeDto save(PostTypeDto postTypeDto) {

        PostTypeDto savedPostTypeDto = null;
        try {
            PostTypeDomain postTypeDomain =  convertDtoToDomain(postTypeDto);
            PostTypeDomain savedPostType = postTypeDao.save(postTypeDomain);
            log.info("PostType save successful");

            savedPostTypeDto = convertDomainToDto(savedPostType);
        }catch (Exception e){
             new ErrorResponse("Error creating a Post Type", e.getMessage());
        }
        return savedPostTypeDto;
    }

    @Override
    public PostTypeDto updateById(int id, PostTypeDto postTypeDto) {
        try {
            postTypeDto.setId(id);
            log.info("PostType update successful");
        }
        catch (Exception e){
            new ErrorResponse("Error updating a Post Type", e.getMessage());
        }
        return save(postTypeDto);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting PostType");
        try {
            PostTypeDomain postTypeDomain = postTypeDao.findById(id).orElse(null);
            if (postTypeDomain != null) {
                //postTypeDao.delete(postTypeDomain);// descomentar para borrar en la base de datos
                postTypeDomain.setIsDeleted(true);   // comentar para borrar en la base de datos
                postTypeDao.save(postTypeDomain);    // comentar para borrar en la base de datos
                log.info("PostType delete successful");
            }
        } catch (Exception e){
            new ErrorResponse("Error deleting a Post Type", e.getMessage());
        }
    }

    @Override
    public PostTypeDto getById(int id) {
        log.info("Get PostType by id");
        PostTypeDto postTypeDto = null;
        try {
            Optional<PostTypeDomain> postTypeDomainOptional = postTypeDao.findById(id);
            if (postTypeDomainOptional.isPresent()) {
                PostTypeDomain postTypeDomain = postTypeDomainOptional.get();

                postTypeDto = convertDomainToDto(postTypeDomain);
                log.info("PostType get successful");
            }
        }
        catch (Exception e){
            new ErrorResponse("Error getting a Post Type", e.getMessage());
        }
        return postTypeDto;
    }

    @Override
    public Page<PostTypeDto> getAllPostTypes(Pageable pageable, String name, String description) {
        log.info("Get All PostType");
        if (name == null && description == null) {

            Page<PostTypeDomain> postTypesPage = postTypeDao.findAllByIsDeletedFalse(pageable);
            return postTypesPage.map(this::convertDomainToDto);
        }

        Page<PostTypeDomain> postTypesPage = postTypeDao.findByNameContainingAndDescriptionContaining(name, description, pageable);

        return postTypesPage.map(this::convertDomainToDto);
    }

    @Override
    protected PostTypeDto convertDomainToDto(PostTypeDomain postTypeDomain) {
       log.info("Converting PostTypeDomain to PostTypeDto");

        PostTypeDto postTypeDto = null;
        try {
            postTypeDto = new PostTypeDto();
            postTypeDto.setId(postTypeDomain.getId());
            postTypeDto.setName(postTypeDomain.getName());
            postTypeDto.setDescription(postTypeDomain.getDescription());

        }
        catch (Exception e){
            log.info("Error converting PostTypeDomain to PostTypeDto");
            new ErrorResponse("Error converting PostTypeDomain to PostTypeDto", e.getMessage());
        }
        return postTypeDto;
    }

    @Override
    protected PostTypeDomain convertDtoToDomain(PostTypeDto postTypeDto) {
        log.info("Converting PostTypeDto to PostTypeDomain");

        PostTypeDomain postTypeDomain = null;
        try {
            postTypeDomain = new PostTypeDomain();
            postTypeDomain.setId(postTypeDto.getId());
            postTypeDomain.setName(postTypeDto.getName());
            postTypeDomain.setDescription(postTypeDto.getDescription());
        }
        catch (Exception e){
            log.info("Error converting PostTypeDto to PostTypeDomain");
            new ErrorResponse("Error converting PostTypeDto to PostTypeDomain", e.getMessage());
        }
        return postTypeDomain;
    }

}
