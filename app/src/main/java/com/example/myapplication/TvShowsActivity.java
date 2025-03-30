package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "f339571cd5d90bc05b2b32cd85fa6e11"; // Replace with your TMDB API Key

    private ImageView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvshows);

        // Initialize ImageViews
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);

        fetchTVShows();
    }

    private void fetchTVShows() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getPopularTVShows(API_KEY, "en-US").enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TVShow> tvShows = response.body().getTVShows();

                    if (!tvShows.isEmpty()) {
                        // Assign TV Show posters to ImageViews
                        loadTVShowPoster(tv1, tvShows, 0);
                        loadTVShowPoster(tv2, tvShows, 1);
                        loadTVShowPoster(tv3, tvShows, 2);
                        loadTVShowPoster(tv4, tvShows, 3);
                        loadTVShowPoster(tv5, tvShows, 4);
                        loadTVShowPoster(tv6, tvShows, 5);
                        loadTVShowPoster(tv7, tvShows, 6);
                        loadTVShowPoster(tv8, tvShows, 7);
                        loadTVShowPoster(tv9, tvShows, 8);
                    }
                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TvShowsActivity.this, "Failed to fetch TV Shows", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTVShowPoster(ImageView imageView, List<TVShow> tvShows, int index) {
        if (index < tvShows.size()) {
            TVShow tvShow = tvShows.get(index);

            // Load image into ImageView using Picasso
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath())
                    .placeholder(R.drawable.button_bg) // Placeholder image while loading
                    .error(R.drawable.nt) // Error image if loading fails
                    .into(imageView);
        }
    }
}
