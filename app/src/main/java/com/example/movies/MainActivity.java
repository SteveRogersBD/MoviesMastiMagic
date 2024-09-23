package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.movies.adapters.NowPlayingAdapter;
import com.example.movies.adapters.PopularMoviesAdapter;
import com.example.movies.adapters.PopularSeriesAdapter;
import com.example.movies.adapters.SearchAdapter;
import com.example.movies.adapters.TopRatedMoviesAdapter;
import com.example.movies.adapters.TopRatedSeriesAdapter;
import com.example.movies.apiInterfaces.MovieApi;
import com.example.movies.apiInterfaces.TVSeriesApi;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.response.NowPlayingResponse;
import com.example.movies.response.PopularMoviesResponse;
import com.example.movies.response.PopularSeriesResponse;
import com.example.movies.response.SearchMoviesResponse;
import com.example.movies.response.TopRatedMoviesResponse;
import com.example.movies.response.TopRatedSeriesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    Retrofit retrofit;
    MovieApi movieApi;
    NowPlayingAdapter nowPlayingAdapter;
    TopRatedMoviesAdapter topratedMoviesAdapter;
    TopRatedSeriesAdapter topRatedSeriesAdapter;
    PopularMoviesAdapter popularMoviesAdapter;
    PopularSeriesAdapter popularSeriesAdapter;
    TVSeriesApi tvSeriesApi;
    ArrayList<SearchMoviesResponse.Result> movieList;
    ArrayList<SearchMoviesResponse.Result> movieListFull;
    SearchAdapter searchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tvSeriesApi = RetrofitInstance.retrofit.create(TVSeriesApi.class);
        movieApi = RetrofitInstance.retrofit.create(MovieApi.class);
        getNowPlaying();
        workWithTopSpinner();
        workWithPopularSpinner();
        workWithSearchView();


    }

    private void workWithSearchView() {
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                    binding.recyclerViewSearch.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void searchMovies(String query) {
        movieApi.getSearchMovies(query,true,"en-US",1).enqueue(new Callback<SearchMoviesResponse>() {
            @Override
            public void onResponse(Call<SearchMoviesResponse> call, Response<SearchMoviesResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    try{
                        movieList = new ArrayList<>();
                        for(SearchMoviesResponse.Result movie: response.body().results)
                        {
                            if(!movie.media_type.equals("person"))
                            {
                                movieList.add(movie);
                            }
                        }

                        searchAdapter = new SearchAdapter(MainActivity.this, movieList);
                        binding.recyclerViewSearch.setAdapter(searchAdapter);
                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        binding.recyclerViewSearch.setVisibility(View.VISIBLE);



                    }catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchMoviesResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void workWithPopularSpinner() {
        String [] items = {"Movie","Series"};
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,items);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerPopular.setAdapter(arrayAdapter);
        binding.spinnerPopular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("Movie")){
                    getPopularMovies();
                }
                else if(item.equals("Series")){
                    getPopularSeries();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void getPopularSeries(){
        tvSeriesApi.getPopularSeries("en-US",1).enqueue(new Callback<PopularSeriesResponse>() {
            @Override
            public void onResponse(Call<PopularSeriesResponse> call, Response<PopularSeriesResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    popularSeriesAdapter = new PopularSeriesAdapter(MainActivity.this,
                            response.body().results);
                    binding.recyclerViewPopular.setAdapter(popularSeriesAdapter);
                    LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this,
                            LinearLayoutManager.HORIZONTAL,false);
                    binding.recyclerViewPopular.setLayoutManager(lm);
                }
            }

            @Override
            public void onFailure(Call<PopularSeriesResponse> call, Throwable throwable) {

            }
        });
    }
    private void getPopularMovies() {
        movieApi.getPopularMovies("en-US",1).enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    try {
                        popularMoviesAdapter = new PopularMoviesAdapter(MainActivity.this,
                                response.body().results);
                        binding.recyclerViewPopular.setAdapter(popularMoviesAdapter);
                        LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.HORIZONTAL,false);
                        binding.recyclerViewPopular.setLayoutManager(lm);

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
            public void onFailure(Call<PopularMoviesResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "404!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNowPlaying(){
        movieApi.getNowPlayingMovies("en-US",1)
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
    }

    private void workWithTopSpinner() {
        String [] items = {"Movie","Series"};
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,items);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerTopRated.setAdapter(arrayAdapter);
        binding.spinnerTopRated.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("Movie")){
                    addTopMovies();
                }
                else if(item.equals("Series")){
                    addTopSeries();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addTopSeries() {
        tvSeriesApi.getTopRatedSeries("en-US",1).enqueue(new Callback<TopRatedSeriesResponse>() {
            @Override
            public void onResponse(Call<TopRatedSeriesResponse> call, Response<TopRatedSeriesResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    try {
                        topRatedSeriesAdapter = new TopRatedSeriesAdapter(MainActivity.this,
                                response.body().results);
                        binding.pager.setAdapter(topRatedSeriesAdapter);
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
            public void onFailure(Call<TopRatedSeriesResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTopMovies() {
        movieApi.getTopRatedMovies("en-US",1).enqueue(new Callback<TopRatedMoviesResponse>() {
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
}