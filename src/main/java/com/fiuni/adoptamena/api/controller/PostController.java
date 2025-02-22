package com.fiuni.adoptamena.api.controller;

import com.fiuni.adoptamena.api.dto.PostDto;
import com.fiuni.adoptamena.api.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
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
