package com.example.movies.response;

import java.util.ArrayList;

public class TopRatedSeriesResponse {
    public int page;
    public ArrayList<Result> results;
    public int total_pages;
    public int total_results;
    public class Result{
        public boolean adult;
        public String backdrop_path;
        public ArrayList<Integer> genre_ids;
        public int id;
        public ArrayList<String> origin_country;
        public String original_language;
        public String original_name;
        public String overview;
        public double popularity;
        public String poster_path;
        public String first_air_date;
        public String name;
        public double vote_average;
        public int vote_count;
    }


}
