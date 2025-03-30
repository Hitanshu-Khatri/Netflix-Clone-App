package com.example.myapplication;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("tv/popular")
    Call<TVShowResponse> getPopularTVShows(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );
}

