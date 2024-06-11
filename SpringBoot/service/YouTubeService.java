package com.example.SpringBoot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;
    
    @Value("${omdb.apikey}")
    private String omdbApiKey;

    private RestTemplate restTemplate = new RestTemplate();
    private final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search";

    public String getMovieTrailer(String query) {
        String url = String.format("%s?part=snippet&type=video&q=%s trailer&key=%s", YOUTUBE_API_URL, query, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray items = jsonObject.getJSONArray("items");

        if (items.length() > 0) {
            JSONObject firstItem = items.getJSONObject(0);
            JSONObject id = firstItem.getJSONObject("id");
            return "https://www.youtube.com/watch?v=" + id.getString("videoId");
        }
        return null;
    }
    
    public MovieDetails getMovieDetails(String title) {
    	String url = "http://www.omdbapi.com/?t=" + title + "&apikey=" + omdbApiKey + "&plot=full";
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);
        if (json.has("Response") && json.getString("Response").equals("True")) {
            String rating = json.has("imdbRating") ? json.getString("imdbRating") : "N/A";
            String poster = json.has("Poster") ? json.getString("Poster") : null;
            String plot = json.has("Plot") ? json.getString("Plot") : "N/A";
            String review = json.has("Review") ? json.getString("review") : "N/A";
            return new MovieDetails(rating, poster, plot, review);
        }
        return null;
    }

    public static class MovieDetails {
        private String rating;
        private String posterUrl;
        private String plot;
        private String review;

        public MovieDetails(String rating, String posterUrl, String plot, String review) {
            this.rating = rating;
            this.posterUrl = posterUrl;
            this.plot = plot;
        }

        public String getRating() {
            return rating;
        }

        public String getPosterUrl() {
            return posterUrl;
        }

        public String getPlot() {
            return plot;
        }

        public String getReview() {
            return review;
    }
    }
}
