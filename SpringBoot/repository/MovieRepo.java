package com.example.SpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBoot.entity.Movie;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {

	List<Movie> findByTitleContainingIgnoreCaseOrCommentContainingIgnoreCase(String title, String comments);

	

	List<Movie> findByTitleContainingIgnoreCase(String query);


}

