package com.gtxc.jwttut.repository;

/*
    Created by gt at 3:02 PM on Wednesday, March 23, 2022.
    Project: jwt-tut, Package: com.gtxc.jwttut.repository.
*/

import com.gtxc.jwttut.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
