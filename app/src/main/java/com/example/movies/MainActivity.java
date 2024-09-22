package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.movies.adapters.NowPlayingAdapter;
import com.example.movies.adapters.TopRatedMoviesAdapter;
import com.example.movies.apiInterfaces.NowPlayingApi;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.response.NowPlayingResponse;
import com.example.movies.response.TopRatedMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    Retrofit retrofit;
    NowPlayingApi nowPlayingApi;
    NowPlayingAdapter nowPlayingAdapter;
    TopRatedMoviesAdapter topratedMoviesAdapter;
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
        workWithSpinner();
        nowPlayingApi.getTopRatedMovies("en-US",1).enqueue(new Callback<TopRatedMoviesResponse>() {
            @Override
            public void onResponse(Call<TopRatedMoviesResponse> call, Response<TopRatedMoviesResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    try {
                        topratedMoviesAdapter = new TopRatedMoviesAdapter(MainActivity.this,
                                response.body().results);
                        binding.pager.setAdapter(topratedMoviesAdapter);
                    }catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopRatedMoviesResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void workWithSpinner() {
        String [] items = {"Movie","Series"};
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,items);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerTopRated.setAdapter(arrayAdapter);

        binding.spinnerTopRated.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}