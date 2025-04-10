package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w342"; // Choose appropriate size (w92, w154, w185, w342, w500, w780, original)

    @SerializedName("id")
    private int id;

    @SerializedName("media_type")
    private String mediaType; // "movie", "tv", "person"

    // --- Movie specific ---
    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    // --- TV specific ---
    @SerializedName("name")
    private String name; // Use this for TV show title

    @SerializedName("first_air_date")
    private String firstAirDate;

    // --- Common ---
    @SerializedName("poster_path")
    private String posterPath; // Relative path, need to append base URL

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private double voteAverage;

    // --- Getters ---
    public int getId() { return id; }
    public String getMediaType() { return mediaType; }
    public String getTitle() { return title; }
    public String getReleaseDate() { return releaseDate; }
    public String getName() { return name; }
    public String getFirstAirDate() { return firstAirDate; }
    public String getPosterPath() { return posterPath; }
    public String getOverview() { return overview; }
    public double getVoteAverage() { return voteAverage; }

    // --- Helper Methods ---

    /** Gets the display name (Title for movie, Name for TV) */
    public String getDisplayName() {
        if ("movie".equals(mediaType) && title != null && !title.isEmpty()) {
            return title;
        } else if ("tv".equals(mediaType) && name != null && !name.isEmpty()) {
            return name;
        } else if (name != null && !name.isEmpty()) { // Fallback for people etc.
            return name;
        } else if (title != null && !title.isEmpty()) {
            return title; // Fallback
        }
        return "Unknown Title";
    }

    /** Gets the full URL for the poster image */
    public String getPosterUrl() {
        if (posterPath != null && !posterPath.isEmpty()) {
            return IMAGE_BASE_URL + POSTER_SIZE + posterPath;
        }
        return null; // No poster available
    }

    /** Gets a formatted media type string */
    public String getFormattedMediaType() {
        if (mediaType == null) return "Unknown";
        switch (mediaType) {
            case "movie": return "Movie";
            case "tv": return "TV Show";
            case "person": return "Person";
            default: return mediaType; // Return raw type if not recognized
        }
    }
}
