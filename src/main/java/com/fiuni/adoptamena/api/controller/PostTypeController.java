package com.fiuni.adoptamena.api.controller;

import com.fiuni.adoptamena.api.dto.PostTypeDto;
import com.fiuni.adoptamena.api.service.IPostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<PostTypeDto> update(@PathVariable(name = "id", required = true) int id,@RequestBody() PostTypeDto postTypeDto) {

        PostTypeDto data = this.postTypeService.updateById(id, postTypeDto);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable(name = "id", required = true) int id) {
        this.postTypeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Se ha eliminado el tipo de post" + id);
    }

}
