package com.fiuni.adoptamena.api.controller.profile;

import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
import com.fiuni.adoptamena.api.service.profile.IProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private IProfileService profileService;

    @GetMapping("/{id}/profile")
    public ResponseEntity<ProfileDTO> getMethodName(@PathVariable Integer id) {
        ProfileDTO result = profileService.getById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PutMapping("/{id}/profile")
    public ResponseEntity<ProfileDTO> updateMethodName(@PathVariable Integer id, @RequestBody ProfileDTO profile) {
        log.info("llega a controller");
        ProfileDTO result = profileService.updateById(id, profile);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMethodName(@PathVariable Integer id) {
        profileService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}