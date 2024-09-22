package com.example.movies.response;

import java.util.ArrayList;

public class TopRatedMoviesResponse {
    public int page;
    public ArrayList<Result> results;
    public int total_pages;
    public int total_results;
    public class Result{
        public String backdrop_path;
        public int id;
        public String name;
        public String original_name;
        public String overview;
        public String poster_path;
        public String media_type;
        public boolean adult;
        public String original_language;
        public ArrayList<Integer> genre_ids;
        public double popularity;
        public String first_air_date;
        public double vote_average;
        public int vote_count;
        public ArrayList<String> origin_country;
        public String title;
        public String original_title;
        public String release_date;
        public boolean video;
    }

}
