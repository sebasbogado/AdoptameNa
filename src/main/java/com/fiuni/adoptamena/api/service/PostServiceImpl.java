package com.fiuni.adoptamena.api.service;

import com.fiuni.adoptamena.api.dao.post.IPostDao;
import com.fiuni.adoptamena.api.dao.post.IPostTypeDao;
import com.fiuni.adoptamena.api.dao.user.IUserDao;
import com.fiuni.adoptamena.api.domain.post.PostDomain;
import com.fiuni.adoptamena.api.domain.post.PostTypeDomain;
import com.fiuni.adoptamena.api.domain.user.UserDomain;
import com.fiuni.adoptamena.api.dto.PostDto;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements IPostService{
    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private IPostDao postDao;

    @Autowired
    private IPostTypeDao postTypeDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public PostDto save(PostDto postDto) {
        try {
            PostDomain postDomain = convertDtoToDomain(postDto);
            postDao.save(postDomain);
            log.info("Post save successful");
        }catch (Exception e){
            new ErrorResponse("Error creating post", e.getMessage());
        }
        return postDto;
    }

    @Override
    public PostDto updateById(int id, PostDto postDto) {
        try {
            postDto.setId(id);
            log.info("Post update successful");
        }catch (Exception e){
            new ErrorResponse("Error updating post", e.getMessage());
        }
        return save(postDto);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting post");
        try {
            PostDomain postDomain = postDao.findById(id).orElse(null);
            if(postDomain != null){
                postDao.delete(postDomain);
                log.info("Post delete successful");
            }
        }
        catch (Exception e){
            new ErrorResponse("Error deleting post", e.getMessage());
        }
    }

    @Override
    public PostDto getById(int id) {
        log.info("Getting post by id");
        PostDto postDto = null;
        try {
            Optional<PostDomain> postDomainOptional = postDao.findById(id);
            if(postDomainOptional.isPresent()){
                PostDomain postDomain = postDomainOptional.get();

                postDto = convertDomainToDto(postDomain);
                log.info("Post get successful");
            }
        }
        catch (Exception e){
            new ErrorResponse("Error getting post", e.getMessage());
        }
        return postDto;
    }

    private PostDto convertDomainToDto(PostDomain postDomain) {
        log.info("Converting PostDomain to postDto");

        PostDto postDto = null;
        try {
            postDto = new PostDto();
            postDto.setId(postDomain.getId());
            postDto.setTitle(postDomain.getTitle());
            postDto.setContent(postDomain.getContent());
            postDto.setDatePost(postDomain.getDatePost());
            postDto.setContactNumber(postDomain.getContactNumber());
            postDto.setLocation_coordinates(postDomain.getLocation_coordinates());
            postDto.setSharedCouter(postDomain.getSharedCounter());
            postDto.setStatus(postDomain.getStatus());
            UserDomain userDomain = userDao.getById(postDto.getId_user());
            postDto.setId_user(userDomain.getId());
            PostTypeDomain postTypeDomain = postTypeDao.findById(postDto.getId_post_type()).get();
            postDto.setId_post_type(postTypeDomain.getId());

        }catch (Exception e){
            new ErrorResponse("Error getting post", e.getMessage());
        }
        return postDto;
    }

    private PostDomain convertDtoToDomain(PostDto postDto) {
        log.info("Converting dto to domain");
        PostDomain postDomain = null;
        try {
            postDomain = new PostDomain();
            postDomain.setId(postDto.getId());
            postDomain.setTitle(postDto.getTitle());
            postDomain.setContent(postDto.getContent());
            postDomain.setDatePost(postDto.getDatePost());
            postDomain.setContactNumber(postDto.getContactNumber());
            postDomain.setLocation_coordinates(postDto.getLocation_coordinates());
            postDomain.setSharedCounter(postDto.getSharedCouter());
            postDomain.setStatus(postDto.getStatus());
            UserDomain userDomain = userDao.getById(postDto.getId_user());
            postDomain.setUser(userDomain);
            PostTypeDomain postTypeDomain = postTypeDao.findById(postDto.getId_post_type()).get();
            postDomain.setPostType(postTypeDomain);

        }catch (Exception e){
            new ErrorResponse("Error converting dto to domain", e.getMessage());
        }
        return postDomain;
    }
}
