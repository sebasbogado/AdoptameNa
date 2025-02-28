package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.dao.post.IPostTypeDao;
import com.fiuni.adoptamena.api.domain.post.PostTypeDomain;
import com.fiuni.adoptamena.api.dto.post.PostTypeDTO;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.exceptions.ResourceNotFoundException;
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
public class PostTypeServiceImpl extends BaseServiceImpl<PostTypeDomain, PostTypeDTO> implements IPostTypeService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);
    @Autowired
    private IPostTypeDao postTypeDao;

    @Override
    public PostTypeDTO save(PostTypeDTO postTypeDto) {

        PostTypeDTO savedPostTypeDTO = null;
        try {
            PostTypeDomain postTypeDomain = convertDtoToDomain(postTypeDto);
            PostTypeDomain savedPostType = postTypeDao.save(postTypeDomain);
            log.info("PostType save successful");

            savedPostTypeDTO = convertDomainToDto(savedPostType);
        } catch (Exception e) {
            log.info("PostType save failed");
            throw new ResourceNotFoundException("Error while saving postType");
            // new ErrorResponse("Error creating a post Type", e.getMessage());
        }
        return savedPostTypeDTO;
    }

    @Override
    public PostTypeDTO updateById(int id, PostTypeDTO postTypeDto) {
        try {
            postTypeDto.setId(id);
            log.info("PostType update successful");
        } catch (Exception e) {
            log.info("PostType update failed");
            throw new ResourceNotFoundException("Error while updating postType");
            // new ErrorResponse("Error updating a post Type", e.getMessage());
        }
        return save(postTypeDto);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting PostType");
        try {
            PostTypeDomain postTypeDomain = postTypeDao.findById(id).orElse(null);
            if (postTypeDomain != null && !postTypeDomain.getIsDeleted()) {
                // postTypeDao.delete(postTypeDomain);// descomentar para borrar en la base de
                // datos
                postTypeDomain.setIsDeleted(true); // comentar para borrar en la base de datos
                postTypeDao.save(postTypeDomain); // comentar para borrar en la base de datos
                log.info("PostType delete successful");
            }
        } catch (Exception e) {
            log.info("PostType delete failed");
            throw new ResourceNotFoundException("Error while deleting postType");
            // new ErrorResponse("Error deleting a post Type", e.getMessage());
        }
    }

    @Override
    public PostTypeDTO getById(int id) {
        log.info("Get PostType by id");
        PostTypeDTO postTypeDto = null;
        try {
            Optional<PostTypeDomain> postTypeDomainOptional = postTypeDao.findById(id);
            if (postTypeDomainOptional.isPresent() && postTypeDomainOptional.get().getIsDeleted()) {
                PostTypeDomain postTypeDomain = postTypeDomainOptional.get();

                postTypeDto = convertDomainToDto(postTypeDomain);
                log.info("PostType get successful");
            }
        } catch (Exception e) {
            log.info("PostType get failed");
            throw new ResourceNotFoundException("Error while getting postType");
            // new ErrorResponse("Error getting a post Type", e.getMessage());
        }
        return postTypeDto;
    }

    @Override
    public Page<PostTypeDTO> getAllPostTypes(Pageable pageable, String name, String description) {
        log.info("Get All PostType");
        if (name == null && description == null) {

            Page<PostTypeDomain> postTypesPage = postTypeDao.findAllByIsDeletedFalse(pageable);
            return postTypesPage.map(this::convertDomainToDto);
        }

        Page<PostTypeDomain> postTypesPage = postTypeDao
                .findByNameContainingAndDescriptionContainingAndIsDeletedFalse(name, description, pageable);

        return postTypesPage.map(this::convertDomainToDto);
    }

    @Override
    protected PostTypeDTO convertDomainToDto(PostTypeDomain postTypeDomain) {
        log.info("Converting PostTypeDomain to PostTypeDTO");

        PostTypeDTO postTypeDto = null;
        try {
            postTypeDto = new PostTypeDTO();
            postTypeDto.setId(postTypeDomain.getId());
            postTypeDto.setName(postTypeDomain.getName());
            postTypeDto.setDescription(postTypeDomain.getDescription());

        } catch (Exception e) {
            log.info("Error converting PostTypeDomain to PostTypeDTO");
            throw new ResourceNotFoundException("Error while converting PostTypeDomain to PostTypeDTO");
            // new ErrorResponse("Error converting PostTypeDomain to PostTypeDTO",
            // e.getMessage());
        }
        return postTypeDto;
    }

    @Override
    protected PostTypeDomain convertDtoToDomain(PostTypeDTO postTypeDto) {
        log.info("Converting PostTypeDTO to PostTypeDomain");

        PostTypeDomain postTypeDomain = null;
        try {
            postTypeDomain = new PostTypeDomain();
            postTypeDomain.setId(postTypeDto.getId());
            postTypeDomain.setName(postTypeDto.getName());
            postTypeDomain.setDescription(postTypeDto.getDescription());

            postTypeDomain.setIsDeleted(false);
        } catch (Exception e) {
            log.info("Error converting PostTypeDTO to PostTypeDomain");
            throw new ResourceNotFoundException("Error while converting PostTypeDTO to PostTypeDomain");
            // new ErrorResponse("Error converting PostTypeDTO to PostTypeDomain",
            // e.getMessage());
        }
        return postTypeDomain;
    }

}
