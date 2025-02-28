package com.fiuni.adoptamena.api.controller.profile;

import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
import com.fiuni.adoptamena.api.service.profile.IProfileService;
import com.fiuni.adoptamena.exception_handler.exceptions.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "User")
public class UserController {

    @Autowired
    private IProfileService profileService;

    @GetMapping("/{id}/profile")
    public ResponseEntity<ProfileDTO> getMethodName(@PathVariable Integer id) {
        ProfileDTO result = profileService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<ProfileDTO> updateMethodName(@Valid @PathVariable Integer id, @RequestBody ProfileDTO profile,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        ProfileDTO result = profileService.updateById(id, profile);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMethodName(@PathVariable Integer id) {
        profileService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}