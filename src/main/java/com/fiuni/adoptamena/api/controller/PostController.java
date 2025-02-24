package com.fiuni.adoptamena.api.controller;

import com.fiuni.adoptamena.api.dto.PostDto;
import com.fiuni.adoptamena.api.service.post.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private IPostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") int id) {
        PostDto data = this.postService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<PostDto>> getAllPosts(
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

        Page<PostDto> postsPage = postService.getAllPosts(pageable, title, content, userId, postTypeId);


        return ResponseEntity.ok(postsPage);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PostDto> create(@RequestBody() PostDto postDto) {

        PostDto data = this.postService.save(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<PostDto> update(@PathVariable(name = "id", required = true) int id, @RequestBody() PostDto postDto) {
        PostDto data = this.postService.updateById(id, postDto);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable(name = "id", required = true) int id) {
        this.postService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post with id: " + id + "was deleted");
    }
}
