package com.example.SpringBoot.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.SpringBoot.entity.Movie;
import com.example.SpringBoot.entity.Moviedata;
import com.example.SpringBoot.repository.MovieRepository;
import com.example.SpringBoot.service.DatabaseService;
import com.example.SpringBoot.service.YouTubeService;


@RestController
public class MovieController<ObjectNode> {

	@Autowired
	DatabaseService databaseService;
	
	@Autowired
    private MovieRepository movieRepository;

    @Autowired
    private YouTubeService youtubeService;
	
	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchListForm(@RequestParam(required = false) Integer id) {
		
		System.out.println(id);
		String viewName = "watchlistItemForm";
		
		Map<String, Object> model = new HashMap<>();
		
		if(id == null) {
			model.put("watchlistItem", new Movie());
		} else {
			model.put("watchlistItem", databaseService.getMovieById(id));
		}
//		Movie dummyMovie = new Movie();
//		dummyMovie.setTitle("dummy");
//		dummyMovie.setRating(0);
//		dummyMovie.setPriority("Low");
//		dummyMovie.setComment("john doe");
//		
//		model.put("watchlistItem", dummyMovie);
		
		
		return new ModelAndView(viewName, model);
	}
	
	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchListForm(@Valid @ModelAttribute("watchlistItem") Movie movie, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			// if errors are there, redisplay the form and let user enter again"
			return new ModelAndView("watchlistItemForm");
		}
		/*
		 if(id == null) {
		 create new movie
		 } else {
		 update 
		 }
		 */
		
		Integer id = movie.getId();
		if(id == null) {
			databaseService.create(movie);
		} else {
			databaseService.update(movie, id);
		}
		

		
		RedirectView rd = new RedirectView();
		rd.setUrl("/watchlist");
		
		return new ModelAndView(rd);
	}
	
	@GetMapping("/watchlist")
	public ModelAndView getWatchlist() {
		// TODO Auto-generated method stub

		String viewName = "watchlist";
		Map<String, Object> model = new HashMap<>();
		List<Movie> movieList = databaseService.getAllMovies();
		model.put("watchlistrows", movieList);
		model.put("noofmovies", movieList.size());
		return new ModelAndView(viewName, model);
	}
	//@GetMapping("/search")
//	public ModelAndView searchMovies(@RequestParam("query") String query) {
	//    List<Movie> searchResults = databaseService.searchMovies(query); // movieService is your service layer
	//   ModelAndView modelAndView = new ModelAndView("index");
	//    modelAndView.addObject("searchResults", searchResults);
	//  return modelAndView;
   //  }

	@PostMapping("/deleteMovie")
	public ModelAndView deleteMovie(@RequestParam Integer id) {
	    databaseService.deleteMovie(id);
	    return new ModelAndView("redirect:/watchlist");
	}
	

	//private final YouTubeService youTubeService;

   // public MovieController(YouTubeService youTubeService) {
      //  this.youTubeService = youTubeService;
   // }

	 //@GetMapping("/search")
	//    public String searchMovie(@RequestParam String query, Model model) {
	//        Moviedata movie = movieRepository.findByTitle(query);
	//        if (movie != null) {
	//            String trailerUrl = youtubeService.getMovieTrailer(query);
	//            model.addAttribute("movie", movie);
	//.addAttribute("trailerUrl", trailerUrl);
	 //       } else {
	 //           model.addAttribute("message", "Movie not found");
	 //       }
	 //       return "searchResults";
	 //   }
}
