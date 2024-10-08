package com.example.movies.apiInterfaces;

import com.example.movies.response.MovieByIDResponse;
import com.example.movies.response.NowPlayingResponse;
import com.example.movies.response.PopularMoviesResponse;
import com.example.movies.response.SearchMoviesResponse;
import com.example.movies.response.TopRatedMoviesResponse;
import com.example.movies.response.TopRatedSeriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/now_playing")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<NowPlayingResponse>getNowPlayingMovies(
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("movie/{id}")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<MovieByIDResponse>getMovieById(
            @Path("id") int movieId,
            @Query("language") String lang
    );

    @GET("movie/top_rated")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<TopRatedMoviesResponse>getTopRatedMovies(
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("movie/popular")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<PopularMoviesResponse>getPopularMovies(
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("search/multi")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<SearchMoviesResponse>getSearchMovies(
            @Query("query") String query,
            @Query("include_adult") boolean includeAdult,
            @Query("language") String language,
            @Query("page") int page
    );



}
