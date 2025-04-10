package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TmdbSearchResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<SearchResult> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    // Getters
    public int getPage() { return page; }
    public List<SearchResult> getResults() { return results; }
    public int getTotalPages() { return totalPages; }
    public int getTotalResults() { return totalResults; }
}
