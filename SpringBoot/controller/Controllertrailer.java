package com.example.SpringBoot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SpringBoot.service.YouTubeService;
import com.example.SpringBoot.service.YouTubeService.MovieDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class Controllertrailer {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private YouTubeService youtubeService;

    @GetMapping("/search")
    public String searchMovie(@RequestParam String query, Model model) {
        logger.info("Searching for movie: {}", query);

        String trailerUrl = youtubeService.getMovieTrailer(query);
        MovieDetails movieDetails = youtubeService.getMovieDetails(query);

        if (trailerUrl != null && movieDetails != null) {
            logger.info("Trailer found: {}", trailerUrl);
            model.addAttribute("title", query);
            model.addAttribute("trailerUrl", trailerUrl);
            model.addAttribute("rating", movieDetails.getRating());
            model.addAttribute("posterUrl", movieDetails.getPosterUrl());
            model.addAttribute("plot", movieDetails.getPlot()); // Add plot to the model
            model.addAttribute("reivew", movieDetails.getReview());
        } else {
            logger.warn("Movie not found: {}", query);
            model.addAttribute("message", "Movie not found");
        }
        return "searchResults";
    }
    @PostMapping("/addReview")
    public String addReview(@RequestParam String movieTitle, @RequestParam String reviewText, RedirectAttributes redirectAttributes) {
        Review.add(new Review(movieTitle, reviewText));
        redirectAttributes.addAttribute("query", movieTitle);
        return "redirect:/search";
    }

    public static class Review {
        private String movieTitle;
        private String reviewText;

        public Review(String movieTitle, String reviewText) {
            this.movieTitle = movieTitle;
            this.reviewText = reviewText;
        }

        public static void add(Review review) {
			// TODO Auto-generated method stub
			
		}

		public String getMovieTitle() {
            return movieTitle;
        }

        public String getReviewText() {
            return reviewText;
        }
    }
}