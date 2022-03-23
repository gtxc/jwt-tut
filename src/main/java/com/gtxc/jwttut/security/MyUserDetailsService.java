package com.gtxc.jwttut.security;

/*
    Created by gt at 3:03 PM on Wednesday, March 23, 2022.
    Project: jwt-tut, Package: com.gtxc.jwttut.security.
*/

import com.gtxc.jwttut.entity.User;
import com.gtxc.jwttut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userRes = userRepository.findByEmail(email);
        if (!userRes.isPresent()) {
            throw new UsernameNotFoundException("Could not find user with email : " + email);
        }
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new
                        SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
