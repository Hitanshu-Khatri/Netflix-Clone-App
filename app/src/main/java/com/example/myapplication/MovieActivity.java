package com.example.myapplication;



import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "f339571cd5d90bc05b2b32cd85fa6e11"; // Replace with your TMDB API Key

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading = false;
    private TextView movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(adapter);

        fetchMovies();



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    currentPage++;
                    fetchMovies();
                }
            }
        });
    }

    private void fetchMovies() {
        isLoading = true;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getPopularMovies(API_KEY, String.valueOf(currentPage)).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.addMovies(response.body().getMovies());
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieActivity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });
    }
}

