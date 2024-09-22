package com.example.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.response.NowPlayingResponse;
import com.example.movies.response.TopRatedMoviesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMoviesAdapter.ViewHolder>{

    Context context;
    ArrayList<TopRatedMoviesResponse.Result> movies;

    public TopRatedMoviesAdapter(Context context, ArrayList<TopRatedMoviesResponse.Result> movies) {
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
        TopRatedMoviesResponse.Result movie = movies.get(position);
        String baseUrl = "https://image.tmdb.org/t/p/w500"; // Example base URL for TMDb
        String imageUrl = baseUrl + movie.poster_path;
        Picasso.get().load(imageUrl).into(holder.posterMovie);
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
