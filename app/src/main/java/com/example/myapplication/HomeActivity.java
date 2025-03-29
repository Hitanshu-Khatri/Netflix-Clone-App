package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout continueWatchingLayout2;
    private LinearLayout continueWatchingLayout3;
    private LinearLayout actionMoviesLayout;
    private LinearLayout comedyMoviesLayout;
    private LinearLayout horrorMoviesLayout;
    private LinearLayout thrillerMoviesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bannerImage = findViewById(R.id.banner_image);
        continueWatchingLayout = findViewById(R.id.continue_watching_layout);
        continueWatchingLayout2 = findViewById(R.id.continue_watching_layout2);
        continueWatchingLayout3 = findViewById(R.id.continue_watching_layout3);
        actionMoviesLayout = findViewById(R.id.action_movies_layout);
        comedyMoviesLayout = findViewById(R.id.comedy_movies_layout);
        horrorMoviesLayout = findViewById(R.id.horror_movies_layout);
        thrillerMoviesLayout = findViewById(R.id.thriller_movies_layout);

        fetchMovies();
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

                    for (int i = 0; i < movies.size(); i++) {
                        Movie movie = movies.get(i);
                        ImageView moviePoster = new ImageView(HomeActivity.this);
                        moviePoster.setLayoutParams(new LinearLayout.LayoutParams(300, 450));
                        moviePoster.setPadding(10, 10, 10, 10);

                        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(moviePoster);

                        if (i % 3 == 0) {
                            continueWatchingLayout.addView(moviePoster);
                        } else if (i % 3 == 1) {
                            continueWatchingLayout2.addView(moviePoster);
                        } else {
                            continueWatchingLayout3.addView(moviePoster);
                        }

                        // Populate genre sections (Clone approach for multiple genres)
                        for (int genreId : movie.getGenreIds()) {
                            ImageView genreMoviePoster = new ImageView(HomeActivity.this);
                            genreMoviePoster.setLayoutParams(new LinearLayout.LayoutParams(300, 450));
                            genreMoviePoster.setPadding(10, 10, 10, 10);
                            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(genreMoviePoster);

                            switch (genreId) {
                                case 28:
                                    actionMoviesLayout.addView(genreMoviePoster);
                                    break;
                                case 35:
                                    comedyMoviesLayout.addView(genreMoviePoster);
                                    break;
                                case 27:
                                    horrorMoviesLayout.addView(genreMoviePoster);
                                    break;
                                case 53:
                                    thrillerMoviesLayout.addView(genreMoviePoster);
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
}
