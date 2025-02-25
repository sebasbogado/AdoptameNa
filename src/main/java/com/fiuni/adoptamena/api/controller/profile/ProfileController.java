package com.fiuni.adoptamena.api.controller.profile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiuni.adoptamena.api.dto.profile.ProfileDTO;
import com.fiuni.adoptamena.api.service.profile.IProfileService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/profiles")
@Slf4j
public class ProfileController {
    @Autowired
    private IProfileService profileService;

    @PostMapping("/{userId}")
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO profile, @PathVariable Integer userId) {
        log.info("Creating profile {}", profile);
        System.out.println("LLEGO A CONTROLLER");
        ProfileDTO profileDTO = profileService.saveProfile(profile, userId);
        return new ResponseEntity<>(profileDTO,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@RequestParam Integer id) {
        ProfileDTO profileDTO = profileService.getById(id);
        return new ResponseEntity<>(profileDTO,HttpStatus.OK);
    }
    
    
}
