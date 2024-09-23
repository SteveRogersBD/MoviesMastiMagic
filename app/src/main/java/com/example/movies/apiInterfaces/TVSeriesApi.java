package com.example.movies.apiInterfaces;

import com.example.movies.response.PopularSeriesResponse;
import com.example.movies.response.TopRatedSeriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TVSeriesApi {
    @GET("tv/top_rated")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<TopRatedSeriesResponse> getTopRatedSeries(
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("tv/popular")
    @Headers({
            "accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzgwMDEwNDEyNjk0MDc2YjUwZDIzNzJkNGIzMWI0NSIsIm5iZiI6MTcyNjczMDMyNS4xNzI5NzEsInN1YiI6IjY2ZDhlZjcwZTg0NDVhYTljYTAxNzk2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kSway11ZrYwKrH7Qv0LQJVEjnonfpqaP0yimRSVT_s0"
    })
    Call<PopularSeriesResponse> getPopularSeries(
            @Query("language") String lang,
            @Query("page") int page
    );
}
