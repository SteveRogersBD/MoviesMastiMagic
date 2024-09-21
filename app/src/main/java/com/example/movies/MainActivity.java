package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.movies.adapters.NowPlayingAdapter;
import com.example.movies.adapters.PopularMoviesAdapter;
import com.example.movies.apiInterfaces.NowPlayingApi;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.response.NowPlayingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    Retrofit retrofit;
    NowPlayingApi nowPlayingApi;
    NowPlayingAdapter nowPlayingAdapter;
    PopularMoviesAdapter popularMoviesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nowPlayingApi = RetrofitInstance.retrofit.create(NowPlayingApi.class);
        nowPlayingApi.getNowPlayingMovies("en-US",1)
                .enqueue(new Callback<NowPlayingResponse>() {
                    @Override
                    public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                        if(response.isSuccessful() && response.body()!=null)
                        {
                            popularMoviesAdapter = new PopularMoviesAdapter(MainActivity.this,
                                    response.body().results);
                            binding.pager.setAdapter(popularMoviesAdapter);
                            nowPlayingAdapter = new NowPlayingAdapter(MainActivity.this,
                                    response.body().results);
                            binding.recyclerView.setAdapter(nowPlayingAdapter);
                            LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this,
                                    LinearLayoutManager.HORIZONTAL,false);
                            binding.recyclerView.setLayoutManager(lm);
                        }
                    }

                    @Override
                    public void onFailure(Call<NowPlayingResponse> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "404!!!", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}