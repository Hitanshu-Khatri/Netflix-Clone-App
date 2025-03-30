package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie2, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.imageView);

        // Set OnClickListener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.putExtra("ITEM_ID", movie.getId()); // Pass movie ID
            intent.putExtra("ITEM_TYPE", "movie"); // Indicate it's a movie
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(List<Movie> newMovies) {
        int startPosition = movies.size();
        movies.addAll(newMovies);
        notifyItemRangeInserted(startPosition, newMovies.size());
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.moviePoster);
        }


    }
}
