package com.example.srijanshukla.SpringBoot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.srijanshukla.SpringBoot.entity.Moviedata;

@Repository
public interface MovieRepository extends JpaRepository<Moviedata, Long> {
    Moviedata findByTitle(String title);
}
