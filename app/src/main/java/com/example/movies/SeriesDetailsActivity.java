package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movies.databinding.ActivitySeriesDetailsBinding;
import com.example.movies.response.PopularSeriesResponse;
import com.example.movies.response.TopRatedSeriesResponse;
import com.squareup.picasso.Picasso;

public class SeriesDetailsActivity extends AppCompatActivity {

    ActivitySeriesDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeriesDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        PopularSeriesResponse.Result body = (PopularSeriesResponse.Result)
                intent.getParcelableExtra("series");
        TopRatedSeriesResponse.Result body1 = (TopRatedSeriesResponse.Result)
                intent.getParcelableExtra("series1");

        if(body!=null) getPopular(body);
        if(body1!=null) getTop(body1);


    }

    private void getTop(TopRatedSeriesResponse.Result body1) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+body1.poster_path)
                .into(binding.posterDetails);
        if(body1.adult==false) binding.adultImageDetails.setVisibility(View.INVISIBLE);
        binding.countryDetails.setText(body1.origin_country.toString());
        binding.releaseDateDetails.setText(body1.first_air_date);
        binding.overview.setText(body1.overview);
        binding.movieTitle.setText(body1.name);
        binding.tvRating.setText(String.format("%.1f", body1.vote_average));

        binding.imageBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // Simulates the back button press
            }
        });
    }

    private void getPopular(PopularSeriesResponse.Result body) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+body.poster_path)
                .into(binding.posterDetails);
        if(body.adult==false) binding.adultImageDetails.setVisibility(View.INVISIBLE);
        binding.countryDetails.setText(body.origin_country.toString());
        binding.releaseDateDetails.setText(body.first_air_date);
        binding.overview.setText(body.overview);
        binding.movieTitle.setText(body.name);
        binding.tvRating.setText(String.format("%.1f", body.vote_average));

        binding.imageBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // Simulates the back button press
            }
        });
    }
}