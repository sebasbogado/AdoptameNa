package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.dao.post.IPostDao;
import com.fiuni.adoptamena.api.dao.post.IPostTypeDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.post.PostDomain;
import com.fiuni.adoptamena.api.domain.post.PostTypeDomain;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.dto.PostDto;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
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
public class PostServiceImpl extends BaseServiceImpl<PostDomain, PostDto> implements IPostService {
    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private IPostDao postDao;

    @Autowired
    private IPostTypeDao postTypeDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public PostDto save(PostDto postDto) {
        PostDto savedPostDto = null;
        try {
            PostDomain postDomain = convertDtoToDomain(postDto);
            PostDomain savedPostDomain = postDao.save(postDomain);
            log.info("Post save successful");

            savedPostDto = convertDomainToDto(savedPostDomain);
        } catch (Exception e) {
            new ErrorResponse("Error creating post", e.getMessage());
        }
        return savedPostDto;
    }

    @Override
    public PostDto updateById(int id, PostDto postDto) {
        try {
            postDto.setId(id);
            log.info("Post update successful");
        } catch (Exception e) {
            new ErrorResponse("Error updating post", e.getMessage());
        }
        return save(postDto);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting post");
        try {
            PostDomain postDomain = postDao.findById(id).orElse(null);
            if (postDomain != null) {
                //postDao.delete(postDomain);  // descomentar para borrar en la base de datos
                postDomain.setIsDeleted(true); // comentar para borrar en la base de datos
                postDao.save(postDomain);      // comentar para borrar en la base de datos
                log.info("Post delete successful");
            }
        } catch (Exception e) {
            new ErrorResponse("Error deleting post", e.getMessage());
        }
    }

    @Override
    public PostDto getById(int id) {
        log.info("Getting post by id");
        PostDto postDto = null;
        try {
            Optional<PostDomain> postDomainOptional = postDao.findById(id);
            if (postDomainOptional.isPresent()) {
                PostDomain postDomain = postDomainOptional.get();
                postDto = convertDomainToDto(postDomain);
                log.info("Post get successful");
            }
        } catch (Exception e) {
            new ErrorResponse("Error getting post", e.getMessage());
        }
        return postDto;
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable, String title, String content, Integer userId, Integer postTypeId) {
        log.info("Getting all posts");

        if (title != null || content != null || userId != null || postTypeId != null) {
            Page<PostDomain> postPage = postDao.findByFilters(pageable, title, content, userId, postTypeId);
            return postPage.map(this::convertDomainToDto);
        }

        Page<PostDomain> postPage = postDao.findAllByIsDeletedFalse(pageable);
        return postPage.map(this::convertDomainToDto);
    }

    @Override
    protected PostDto convertDomainToDto(PostDomain postDomain) {
        log.info("Converting PostDomain to PostDto");

        PostDto postDto = null;
        try {
            postDto = new PostDto();
            postDto.setId(postDomain.getId());
            postDto.setTitle(postDomain.getTitle());
            postDto.setContent(postDomain.getContent());
            postDto.setPublicationDate(postDomain.getPublicationDate());
            postDto.setContactNumber(postDomain.getContactNumber());
            postDto.setLocationCoordinates(postDomain.getLocationCoordinates());
            postDto.setSharedCounter(postDomain.getSharedCounter());
            postDto.setStatus(postDomain.getStatus());

            if (postDomain.getUser() != null && postDomain.getUser().getId() != null) {
                postDto.setId_user(postDomain.getUser().getId());
            } else {
                throw new ResourceNotFoundException("User not found");
            }

            if (postDomain.getPostType() != null && postDomain.getPostType().getId() != null) {
                postDto.setIdPostType(postDomain.getPostType().getId());
            } else {
                throw new ResourceNotFoundException("Post Type ID is null or invalid");
            }
        } catch (Exception e) {
            log.error("Error converting PostDomain to PostDto", e);
            new ErrorResponse("Error converting PostDomain to PostDto", e.getMessage());
        }
        return postDto;
    }

    @Override
    protected PostDomain convertDtoToDomain(PostDto postDto) {
        log.info("Converting PostDto to PostDomain");

        PostDomain postDomain = new PostDomain();
        try {
            postDomain.setId(postDto.getId());
            postDomain.setTitle(postDto.getTitle());
            postDomain.setContent(postDto.getContent());
            postDomain.setPublicationDate(postDto.getPublicationDate());
            postDomain.setContactNumber(postDto.getContactNumber());
            postDomain.setLocationCoordinates(postDto.getLocationCoordinates());
            postDomain.setSharedCounter(postDto.getSharedCounter());
            postDomain.setStatus(postDto.getStatus());

            if (postDto.getId_user() != null) {
                UserDomain userDomain = userDao.findById(postDto.getId_user()).orElse(null);
                //verificar si existe
                if (userDomain != null) {
                    postDomain.setUser(userDomain);
                } else {
                    throw new ResourceNotFoundException("User not found");
                }

            } else {
                throw new ResourceNotFoundException("Post Type ID is null or invalid");
            }

            if (postDto.getIdPostType() != null) {
                PostTypeDomain postTypeDomain = postTypeDao.findById(postDto.getIdPostType())
                        .orElseThrow(() -> new RuntimeException("PostType not found"));
                postDomain.setPostType(postTypeDomain);
            } else {
                throw new ResourceNotFoundException("Post Type ID is null or invalid");
            }

        } catch (Exception e) {
            log.error("Error converting PostDto to PostDomain", e);
            new ErrorResponse("Error converting PostDto to PostDomain", e.getMessage());
        }
        return postDomain;
    }
}
