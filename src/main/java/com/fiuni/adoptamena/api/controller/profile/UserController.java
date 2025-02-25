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

    @GetMapping("/profiles")
    public String getOk() {
        return "OK";
    }
    @GetMapping("/{id}/profile")
    public ResponseEntity<ProfileDTO> getMethodName(@PathVariable Integer id) {
        ProfileDTO result = profileService.getById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    
}