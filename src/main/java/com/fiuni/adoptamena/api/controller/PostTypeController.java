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
        return ResponseEntity.status(201).body(data);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<PostTypeDto> update(@PathVariable(name = "id", required = true) int id,@RequestBody() PostTypeDto postTypeDto) {

        PostTypeDto data = this.postTypeService.updateById(id, postTypeDto);
        return ResponseEntity.status(201).body(data);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable(name = "id", required = true) int id) {
        this.postTypeService.deleteById(id);
        return ResponseEntity.status(200).body("Se ha eliminado el tipo de post");
    }

}
