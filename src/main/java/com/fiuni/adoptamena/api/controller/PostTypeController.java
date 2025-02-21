package com.fiuni.adoptamena.api.controller;

import com.fiuni.adoptamena.api.dto.PostTypeDto;
import com.fiuni.adoptamena.api.service.IPostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postTypes")
public class PostTypeController {

    @Autowired
    private IPostTypeService postTypeService;

    @PostMapping({"", "/"})
    public ResponseEntity<PostTypeDto> create(@RequestBody() PostTypeDto postTypeDto) {

        PostTypeDto data = this.postTypeService.save(postTypeDto);
        return ResponseEntity.ok(postTypeService.createPostType(postTypeDto));
    }
}
