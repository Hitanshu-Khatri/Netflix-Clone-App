package com.example.myapplication; // Using the package name you provided

// No longer need imports for OkHttpClient or HttpLoggingInterceptor for basic setup

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // The singleton pattern ensures we only create the Retrofit instance once
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
                    // No .client() call needed here - Retrofit will create a default OkHttpClient
                    .build();
        }
        return retrofit;
    }

    // Make sure your interface is named ApiService or change this line accordingly
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}