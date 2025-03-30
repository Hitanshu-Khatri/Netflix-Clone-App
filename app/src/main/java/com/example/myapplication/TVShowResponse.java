package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TVShowResponse {
    @SerializedName("results")
    private List<TVShow> tvShows;

    public List<TVShow> getTVShows() {
        return tvShows;
    }
}
