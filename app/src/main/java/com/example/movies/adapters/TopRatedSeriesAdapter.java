package com.example.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.MovieDetailsActivity;
import com.example.movies.R;
import com.example.movies.SeriesDetailsActivity;
import com.example.movies.response.TopRatedMoviesResponse;
import com.example.movies.response.TopRatedSeriesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopRatedSeriesAdapter extends RecyclerView.Adapter<TopRatedSeriesAdapter.ViewHolder>{

    Context context;
    ArrayList<TopRatedSeriesResponse.Result> movies;

    public TopRatedSeriesAdapter(Context context, ArrayList<TopRatedSeriesResponse.Result> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopRatedSeriesResponse.Result movie = movies.get(position);
        String baseUrl = "https://image.tmdb.org/t/p/w500"; // Example base URL for TMDb
        String imageUrl = baseUrl + movie.poster_path;
        Picasso.get().load(imageUrl).into(holder.posterMovie);
        holder.posterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeriesDetailsActivity.class);
                intent.putExtra("series1",movie);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterMovie = itemView.findViewById(R.id.poster_pager);

        }
    }
}
