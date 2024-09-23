package com.example.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.MovieDetailsActivity;
import com.example.movies.R;
import com.example.movies.SeriesDetailsActivity;
import com.example.movies.response.SearchMoviesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    Context context;
    ArrayList<SearchMoviesResponse.Result>movieList;
    ArrayList<SearchMoviesResponse.Result>movieListFull;

    public SearchAdapter(Context context, ArrayList<SearchMoviesResponse.Result> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public SearchAdapter(Context context, ArrayList<SearchMoviesResponse.Result> movieList,
                         ArrayList<SearchMoviesResponse.Result> movieListFull) {
        this.context = context;
        this.movieList = movieList;
        this.movieListFull = movieListFull;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchMoviesResponse.Result movie = movieList.get(position);
        holder.textView.setText(movie.title);
        String image_url = "https://image.tmdb.org/t/p/w500"+movie.poster_path;
        Picasso.get().load(image_url).into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movie.media_type.equals("movie"))
                {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movieId",movie.id);
                    context.startActivity(intent);
                }
                else if(movie.media_type.equals("tv"))
                {
                    Intent intent = new Intent(context, SeriesDetailsActivity.class);
                    intent.putExtra("series",movie.id);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_image);
            textView = itemView.findViewById(R.id.search_text);
            constraintLayout = itemView.findViewById(R.id.main_layout_search);
        }
    }
}
