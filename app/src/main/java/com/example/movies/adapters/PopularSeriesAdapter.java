package com.example.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.MovieDetailsActivity;
import com.example.movies.R;
import com.example.movies.SeriesDetailsActivity;
import com.example.movies.response.PopularMoviesResponse;
import com.example.movies.response.PopularSeriesResponse;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class PopularSeriesAdapter extends RecyclerView.Adapter<PopularSeriesAdapter.ViewHolder>{

    Context context;
    ArrayList<PopularSeriesResponse.Result> movieList;

    public PopularSeriesAdapter(Context context, ArrayList<PopularSeriesResponse.Result> movieList) {
        this.context = context;
        this.movieList = movieList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularSeriesResponse.Result movie = movieList.get(position);
        holder.textView.setText(movie.name);
        String baseUrl = "https://image.tmdb.org/t/p/w500"; // Example base URL for TMDb
        String imageUrl = baseUrl + movie.poster_path;
        Picasso.get().load(imageUrl).into(holder.imageView);

        String rYear = movie.first_air_date.substring(0,4);
        holder.releaseYear.setText(rYear);
        String rateS = String.format("%.1f",movie.vote_average);
        holder.rating.setText(rateS);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeriesDetailsActivity.class);
                intent.putExtra("series", movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,releaseYear,rating;
        LinearLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_poster);
            textView = itemView.findViewById(R.id.title);
            releaseYear = itemView.findViewById(R.id.tv_releaseyear_final);
            rating = itemView.findViewById(R.id.tv_rating_final);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
