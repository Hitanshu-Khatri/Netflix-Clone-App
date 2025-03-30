package com.example.myapplication;
import com.google.gson.annotations.SerializedName;

public class TVShow {
    @SerializedName("poster_path")
    private String posterPath;

    public String getPosterPath() {
        return posterPath;
    }
}
