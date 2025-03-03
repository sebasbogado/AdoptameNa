package com.fiuni.adoptamena.api.controller.race;

import com.fiuni.adoptamena.api.dto.race.RaceDTO;
import com.fiuni.adoptamena.api.service.race.IRaceService;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/race")
@Slf4j
@Tag(name = "Race")
public class RaceController {

    @Autowired
    private IRaceService raceService;

    @GetMapping("/{id}")
    public ResponseEntity<RaceDTO> get(@PathVariable Integer id) {
        RaceDTO result = raceService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RaceDTO> create(@Valid @RequestBody RaceDTO race, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        RaceDTO result = raceService.create(race);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceDTO> update(@Valid @PathVariable Integer id, @RequestBody RaceDTO race,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        race.setId(id);
        RaceDTO result = raceService.update(race);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        raceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RaceDTO>> getAll(Pageable pageable) {
        return new ResponseEntity<>(raceService.getAll(pageable), HttpStatus.OK);
    }
}
 