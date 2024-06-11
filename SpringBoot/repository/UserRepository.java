package com.example.SpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBoot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

