package com.example.myapplication;

// Replace with your package name

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager; // Or GridLayoutManager
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchResultsAdapter.OnItemClickListener {

    private static final String TAG = "SearchActivity";
    // --- IMPORTANT: REPLACE WITH YOUR ACTUAL TMDB API KEY ---
    // Ideally, store this securely (e.g., in local.properties or BuildConfig)
    // DO NOT HARDCODE YOUR KEY IN PRODUCTION APPS
    private static final String TMDB_API_KEY = "f339571cd5d90bc05b2b32cd85fa6e11";

    private SearchView searchView;
    private RecyclerView recyclerViewSearchResults;
    private SearchResultsAdapter searchAdapter;
    private ProgressBar progressBar;
    private TextView textViewEmpty;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // --- Find Views ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Optional: Set Toolbar as ActionBar
        searchView = findViewById(R.id.searchView);
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        progressBar = findViewById(R.id.progressBar);
        textViewEmpty = findViewById(R.id.textViewEmpty);

        // --- Initialize API Service ---
        apiService = TmdbApiClient.getApiService();

        // --- Setup RecyclerView ---
        setupRecyclerView();

        // --- Setup SearchView ---
        setupSearchView();

        // --- Basic Check ---
        if (TMDB_API_KEY.equals("f339571cd5d90bc05b2b32cd85fa6e11")) {
            Toast.makeText(this, "Please replace 'YOUR_TMDB_API_KEY' in SearchActivity.java", Toast.LENGTH_LONG).show();
            Log.e(TAG, "TMDB API Key is not set!");
        }
    }

    private void setupRecyclerView() {
        // Use LinearLayoutManager for a list like the left screenshots
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(this));
        // OR Use GridLayoutManager for a grid like the right screenshots
        // int spanCount = 3; // Adjust number of columns
        // recyclerViewSearchResults.setLayoutManager(new GridLayoutManager(this, spanCount));

        searchAdapter = new SearchResultsAdapter(this, this);
        recyclerViewSearchResults.setAdapter(searchAdapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // User pressed search button or enter
                if (!query.trim().isEmpty()) {
                    performSearch(query.trim());
                    hideKeyboard();
                }
                return true; // Indicate the query was handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when text changes. You could implement search-as-you-type here,
                // but BE CAREFUL with API rate limits. Use debouncing (delaying the call).
                // For simplicity, we only search on submit.
                if (newText.trim().isEmpty()) {
                    // Clear results if search box is cleared
                    searchAdapter.updateData(null);
                    showEmptyState(false); // Hide empty message
                }
                return false; // Let the SearchView handle default behavior
            }
        });

        // Make the search view focused and open keyboard initially (optional)
        // searchView.setIconified(false); // Already done in XML
        searchView.requestFocus();
    }

    private void performSearch(String query) {
        Log.d(TAG, "Performing search for: " + query);
        showLoading(true);
        showEmptyState(false);
        searchAdapter.updateData(null); // Clear previous results

        Call<TmdbSearchResponse> call = apiService.searchMulti(TMDB_API_KEY, query, 1); // Search page 1

        call.enqueue(new Callback<TmdbSearchResponse>() {
            @Override
            public void onResponse(Call<TmdbSearchResponse> call, Response<TmdbSearchResponse> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<SearchResult> results = response.body().getResults();
                    if (results != null && !results.isEmpty()) {
                        Log.d(TAG, "Search successful, results found: " + results.size());
                        searchAdapter.updateData(results);
                        showEmptyState(false);
                    } else {
                        Log.d(TAG, "Search successful, but no results.");
                        searchAdapter.updateData(null); // Clear adapter
                        showEmptyState(true); // Show "No results" message
                    }
                } else {
                    Log.e(TAG, "Search failed. Code: " + response.code() + ", Message: " + response.message());
                    try {
                        Log.e(TAG, "Error Body: " + response.errorBody().string());
                    } catch (Exception e) { /* Ignore */ }
                    searchAdapter.updateData(null);
                    showEmptyState(true); // Show empty state on error too
                    Toast.makeText(SearchActivity.this, "Error fetching results. Check logs.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TmdbSearchResponse> call, Throwable t) {
                showLoading(false);
                Log.e(TAG, "Search network call failed: ", t);
                searchAdapter.updateData(null);
                showEmptyState(true); // Show empty state on failure
                Toast.makeText(SearchActivity.this, "Network error. Check connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        // Optionally disable UI elements during loading
    }

    private void showEmptyState(boolean show) {
        textViewEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        searchView.clearFocus(); // Remove focus from search view
    }

    // --- SearchResultsAdapter.OnItemClickListener Implementation ---
    @Override
    public void onItemClick(SearchResult item) {
        // Handle click on a search result item
        // Example: Show details or navigate to another activity
        Toast.makeText(this, "Clicked: " + item.getDisplayName() + " (Type: " + item.getFormattedMediaType() + ")", Toast.LENGTH_SHORT).show();

         Intent intent = new Intent(this, Result_item_description.class);
         intent.putExtra("TMDB_ID", item.getId());
         intent.putExtra("MEDIA_TYPE", item.getMediaType());
         startActivity(intent);
    }
}
