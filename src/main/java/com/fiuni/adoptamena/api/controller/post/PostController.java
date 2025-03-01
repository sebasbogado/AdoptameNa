package com.fiuni.adoptamena.api.controller.post;

import com.fiuni.adoptamena.api.dto.post.PostDTO;
import com.fiuni.adoptamena.api.service.post.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post")
public class PostController {

    @Autowired
    private IPostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") int id) {
        PostDTO data = this.postService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "user", required = false) Integer userId, // Filtro por usuario
            @RequestParam(value = "postType", required = false) Integer postTypeId // Filtro por tipo de post
    ) {

        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortParams[0])));

        List<PostDTO> postsPage = postService.getAllPosts(pageable, title, content, userId, postTypeId);

        return ResponseEntity.ok(postsPage);
    }

    @PostMapping({ "", "/" })
    public ResponseEntity<PostDTO> create(@RequestBody() PostDTO postDto) {

        PostDTO data = this.postService.create(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping({ "/{id}" })
    public ResponseEntity<PostDTO> update(@PathVariable(name = "id", required = true) Integer id,
            @RequestBody() PostDTO postDto) {
        postDto.setId(id);
        PostDTO data = this.postService.update(postDto);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping({ "/{id}" })
    public ResponseEntity<String> delete(@PathVariable(name = "id", required = true) Integer id) {
        this.postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("post with id: " + id + "was deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPostByKeyword(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort,
            @RequestParam(value = "keyword") String keyword) {
        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortParams[0])));

        List<PostDTO> postsPage = postService.searchPostByKeyword(pageable, keyword);

        return ResponseEntity.status(HttpStatus.OK).body(postsPage);
    }

    @PutMapping("/{id}/increment-counter")
    public ResponseEntity<String> incrementPostCounter(@PathVariable Integer id) {
        postService.increaseSharedCounter(id);
        return ResponseEntity.ok().body("Shared Counter of the post was increased.");
    }
}