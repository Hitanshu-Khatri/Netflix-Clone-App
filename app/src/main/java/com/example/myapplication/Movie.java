package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }
}
