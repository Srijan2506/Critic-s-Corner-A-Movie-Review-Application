package com.example.SpringBoot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBoot.entity.Moviedata;

@Repository
public interface MovieRepository extends JpaRepository<Moviedata, Long> {
    Moviedata findByTitle(String title);
}