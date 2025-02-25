package com.fiuni.adoptamena.api.controller.profile;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/profiles")
    public String getOk() {
        return "OK";
    }
    
}
/*
 * 
 * 
 * 
 */