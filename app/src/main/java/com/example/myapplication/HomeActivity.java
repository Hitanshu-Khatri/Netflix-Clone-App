package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "f339571cd5d90bc05b2b32cd85fa6e11";

    private ImageView bannerImage;
    private LinearLayout continueWatchingLayout;
    private LinearLayout actionMoviesLayout;
    private LinearLayout comedyMoviesLayout;
    private LinearLayout horrorMoviesLayout;
    private LinearLayout thrillerMoviesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bannerImage = findViewById(R.id.banner_image);
        continueWatchingLayout = findViewById(R.id.continue_watching_container);
        actionMoviesLayout = findViewById(R.id.action_movies_layout);
        comedyMoviesLayout = findViewById(R.id.comedy_movies_layout);
        horrorMoviesLayout = findViewById(R.id.horror_movies_layout);
        thrillerMoviesLayout = findViewById(R.id.thriller_movies_layout);

        fetchMovies();

        ImageButton settingsBtn = findViewById(R.id.imageButton10);
        settingsBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "User Settings", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, languageActivity.class));
        });

        ImageButton searchBtn = findViewById(R.id.imageButton8);
        searchBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Search", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, SearchActivity.class));
        });

        ImageButton downloadBtn = findViewById(R.id.imageButton9);
        downloadBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Downloads", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, DownloadsActivity.class));
        });

        TextView tvShows = findViewById(R.id.tvshows);
        tvShows.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "TV Shows Clicked!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, TvShowsActivity.class));
        });

        TextView movies = findViewById(R.id.movies);
        movies.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Movies Clicked!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MovieActivity.class));
        });
    }

    private void fetchMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getPopularMovies(API_KEY, "en-US").enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getMovies();

                    if (!movies.isEmpty()) {
                        Picasso.get()
                                .load("https://image.tmdb.org/t/p/w780" + movies.get(0).getBackdropPath())
                                .into(bannerImage);
                    }

                    for (Movie movie : movies) {
                        ImageView posterView = createPosterView(movie);
                        continueWatchingLayout.addView(posterView);

                        for (int genreId : movie.getGenreIds()) {
                            ImageView genrePoster = createPosterView(movie);
                            switch (genreId) {
                                case 28:
                                    actionMoviesLayout.addView(genrePoster);
                                    break;
                                case 35:
                                    comedyMoviesLayout.addView(genrePoster);
                                    break;
                                case 27:
                                    horrorMoviesLayout.addView(genrePoster);
                                    break;
                                case 53:
                                    thrillerMoviesLayout.addView(genrePoster);
                                    break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private ImageView createPosterView(Movie movie) {
        ImageView imageView = new ImageView(HomeActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 450);
        params.setMargins(10, 10, 10, 10);
        imageView.setLayoutParams(params);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(imageView);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DescriptionActivity.class);
            intent.putExtra("ITEM_ID", movie.getId());
            startActivity(intent);
        });

        return imageView;
    }
}
