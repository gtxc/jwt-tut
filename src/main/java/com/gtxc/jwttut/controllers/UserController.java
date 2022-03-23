package com.gtxc.jwttut.controllers;

/*
    Created by gt at 3:02 PM on Wednesday, March 23, 2022.
    Project: jwt-tut, Package: com.gtxc.jwttut.controllers.
*/

import com.gtxc.jwttut.entity.User;
import com.gtxc.jwttut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/info")
    public User getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email).orElse(null);
    }
}
