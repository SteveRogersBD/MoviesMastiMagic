package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.movies.adapters.GenreAdapter;
import com.example.movies.apiInterfaces.NowPlayingApi;
import com.example.movies.databinding.ActivityMovieDetailsBinding;
import com.example.movies.response.MovieByIDResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    ActivityMovieDetailsBinding binding;
    NowPlayingApi api;
    GenreAdapter genreAdapter;
    ArrayList<MovieByIDResponse.Genre>genreList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int id = intent.getIntExtra("movieId",0);
        callApi(id);
    }

    private void callApi(int id) {
        api = RetrofitInstance.retrofit.create(NowPlayingApi.class);
        api.getMovieById(id,"en-US").enqueue(new Callback<MovieByIDResponse>() {
            @Override
            public void onResponse(Call<MovieByIDResponse> call, Response<MovieByIDResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    try{
                        inflateLayout(response.body());
                    }catch (Exception e)
                    {
                        Toast.makeText(MovieDetailsActivity.this, e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MovieDetailsActivity.this, response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieByIDResponse> call, Throwable throwable) {
                Toast.makeText(MovieDetailsActivity.this, throwable.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void inflateLayout(MovieByIDResponse body) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+body.poster_path)
                .into(binding.posterDetails);
        if(body.adult==false) binding.adultImageDetails.setVisibility(View.INVISIBLE);
        binding.countryDetails.setText(body.origin_country.toString());
        binding.tvTimer.setText(body.runtime+" min");
        binding.releaseDateDetails.setText(body.release_date);
        binding.reveneuDetails.setText("$" + (body.budget / 1000000.0) + "M");
        binding.overview.setText(body.overview);
        binding.movieTitle.setText(body.title);
        binding.tvRating.setText(String.format("%.1f", body.vote_average));

        binding.imageBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // Simulates the back button press
            }
        });
        genreList = body.genres;
        addGenre();
    }

    private void addGenre() {
        if(genreList!=null)
        {
            genreAdapter = new GenreAdapter(MovieDetailsActivity.this,genreList);
            binding.genreRcycler.setAdapter(genreAdapter);
            LinearLayoutManager lm = new LinearLayoutManager(MovieDetailsActivity.this,
                    LinearLayoutManager.HORIZONTAL,false);
            binding.genreRcycler.setLayoutManager(lm);
            Toast.makeText(this, genreList.size()+"", Toast.LENGTH_SHORT).show();
        }
        else{
            binding.genreRcycler.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Empty!!!", Toast.LENGTH_SHORT).show();
        }
    }
}