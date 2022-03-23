package com.gtxc.jwttut.controllers;

/*
    Created by gt at 3:02 PM on Wednesday, March 23, 2022.
    Project: jwt-tut, Package: com.gtxc.jwttut.controllers.
*/

import com.gtxc.jwttut.entity.User;
import com.gtxc.jwttut.models.LoginCredentials;
import com.gtxc.jwttut.repository.UserRepository;
import com.gtxc.jwttut.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, JWTUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials loginCredentials) {
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword());
            authenticationManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(loginCredentials.getEmail());
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }
}
