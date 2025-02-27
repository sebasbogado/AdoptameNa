package com.fiuni.adoptamena.api.controller;

import com.fiuni.adoptamena.api.dto.PostTypeDto;
import com.fiuni.adoptamena.api.service.post.IPostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postTypes")
public class PostTypeController {

    @Autowired
    private IPostTypeService postTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<PostTypeDto> getPostTypeById(@PathVariable(name = "id") int id) {
        PostTypeDto data = this.postTypeService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<PostTypeDto>> getAllPostTypes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description) {

        // Desglosar el parámetro 'sort' en campo y dirección
        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortParams[0])));

        // Obtener los datos paginados y filtrados usando el servicio
        Page<PostTypeDto> postTypesPage = postTypeService.getAllPostTypes(pageable, name, description);

        // Retornar la respuesta con los datos paginados
        return ResponseEntity.ok(postTypesPage);
    }


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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("PostType with id: " + id + "was deleted");
    }

}
