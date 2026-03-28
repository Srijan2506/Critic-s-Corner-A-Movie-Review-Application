package com.example.srijanshukla.SpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.srijanshukla.SpringBoot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

