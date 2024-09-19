package com.example.movies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.response.NowPlayingResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {

    Context context;
    List<NowPlayingResponse.Result>movies;

    public NowPlayingAdapter(Context context, List<NowPlayingResponse.Result> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NowPlayingResponse.Result movie = movies.get(position);
        holder.textView.setText(movie.title);
        String baseUrl = "https://image.tmdb.org/t/p/w500"; // Example base URL for TMDb
        String imageUrl = baseUrl + movie.poster_path;
        Log.i("imageUrl",imageUrl);
        Picasso.get().load(imageUrl).into(holder.imageView);

        String rYear = movie.release_date.substring(0,4);
        holder.releaseYear.setText(rYear);
        String rateS = String.format("%.1f",movie.vote_average);
        holder.rating.setText(rateS);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,releaseYear,rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_poster);
            textView = itemView.findViewById(R.id.title);
            releaseYear = itemView.findViewById(R.id.tv_releaseyear_final);
            rating = itemView.findViewById(R.id.tv_rating_final);
        }
    }
}
