package com.example.myapplication;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
// Remove unused Button import if playButton/myListButton are commented out
// import android.widget.Button;
import android.widget.ImageButton; // Keep if using later
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // For user feedback on errors

import androidx.annotation.Nullable; // Use Nullable for clarity
import androidx.appcompat.app.AppCompatActivity;

// Using Retrofit (as in the SearchActivity) is generally recommended over Volley
// for consistency, but we'll stick to Volley as per your provided code.
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso; // Using Picasso as provided

import org.json.JSONException;
import org.json.JSONObject;

public class Result_item_description extends AppCompatActivity {

    // --- IMPORTANT: Use your actual API key ---
    private static final String API_KEY = "f339571cd5d90bc05b2b32cd85fa6e11"; // Replace with your TMDb API key
    private static final String TAG = "DescriptionActivity";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String BACKDROP_SIZE = "w780"; // Size for backdrop images

    private ImageView bannerImage;
    private TextView movieTitle; // Rename to itemTitle for clarity?
    private TextView movieDescription; // Rename to itemDescription?
    // private ImageButton playButton, myListButton; // Keep commented if not used yet

    private RequestQueue queue; // Re-use RequestQueue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        // --- Check API Key ---
//        if (API_KEY.equals("Y") || API_KEY.isEmpty()) {
//            Log.e(TAG, "TMDB API Key is not set in DescriptionActivity!");
////            Toast.makeText(this, "API Key not configured.", Toast.LENGTH_LONG).show();
//            // Optionally finish the activity or show an error message prominently
//            // finish();
//            // return;
//        }

        bannerImage = findViewById(R.id.bannerImage);
        movieTitle = findViewById(R.id.movieTitle);
        movieDescription = findViewById(R.id.movieDescription);
        // playButton = findViewById(R.id.playButton);
        // myListButton = findViewById(R.id.myListButton);

        queue = Volley.newRequestQueue(this); // Initialize RequestQueue once

        // --- Get data from Intent ---
        Intent intent = getIntent();
        int itemId = intent.getIntExtra("TMDB_ID", -1); // <<< Use the correct key "TMDB_ID"
        String mediaType = intent.getStringExtra("MEDIA_TYPE"); // Get the media type

        if (itemId != -1 && !TextUtils.isEmpty(mediaType)) {
            Log.d(TAG, "Received Item ID: " + itemId + ", Type: " + mediaType);
            fetchItemDetails(itemId, mediaType);
        } else {
            Log.e(TAG, "Invalid item ID or media type received. ID: " + itemId + ", Type: " + mediaType);
            // Show error to user
            movieTitle.setText("Error");
            movieDescription.setText("Could not load details. Invalid data received.");
            Toast.makeText(this, "Error loading details.", Toast.LENGTH_SHORT).show();
            // Optionally finish();
        }

        // Set click listeners if needed later
        // ...
    }

    private void fetchItemDetails(int itemId, String mediaType) {
        String endpointPath;
        // Determine the correct API endpoint based on media type
        if ("movie".equalsIgnoreCase(mediaType)) {
            endpointPath = "movie/" + itemId;
        } else if ("tv".equalsIgnoreCase(mediaType)) {
            endpointPath = "tv/" + itemId;
        } else {
            Log.e(TAG, "Unsupported media type: " + mediaType);
            Toast.makeText(this, "Unsupported content type.", Toast.LENGTH_SHORT).show();
            movieTitle.setText("Error");
            movieDescription.setText("Cannot load details for this type of content.");
            return; // Don't proceed with the request
        }

        String url = "https://api.themoviedb.org/3/" + endpointPath + "?api_key=" + API_KEY + "&language=en-US";
        Log.d(TAG, "Fetching details from URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> { // Using lambda for Response.Listener
                    Log.d(TAG, "API Response Received: " + response.toString());
                    parseAndDisplayDetails(response, mediaType); // Pass mediaType to parser
                },
                error -> { // Using lambda for Response.ErrorListener
                    String errorMsg = "Unknown Volley error";
                    if (error != null && error.getMessage() != null) {
                        errorMsg = error.getMessage();
                    } else if (error != null && error.networkResponse != null) {
                        errorMsg = "Status Code: " + error.networkResponse.statusCode;
                    }
                    Log.e(TAG, "Volley error fetching details: " + errorMsg, error);
                    movieTitle.setText("Error Loading");
                    movieDescription.setText("Failed to fetch details. Please try again later.");
                    Toast.makeText(this, "Error fetching details.", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonObjectRequest); // Add the request to the queue
    }

    private void parseAndDisplayDetails(JSONObject response, String mediaType) {
        try {
            String title;
            String overview = response.optString("overview", "No description available."); // Use optString for safety
            String backdropPath = response.optString("backdrop_path", null); // Get backdrop path

            // Get title based on media type (movies use "title", TV uses "name")
            if ("movie".equalsIgnoreCase(mediaType)) {
                title = response.optString("title", "No Title");
            } else if ("tv".equalsIgnoreCase(mediaType)) {
                title = response.optString("name", "No Title"); // TV shows use "name"
            } else {
                title = "Unknown Title"; // Fallback
            }

            // --- Update UI ---
            movieTitle.setText(title);
            movieDescription.setText(overview);

            // Load banner image using Picasso (use backdrop_path)
            if (backdropPath != null && !backdropPath.isEmpty() && !backdropPath.equals("null")) {
                String fullBackdropUrl = IMAGE_BASE_URL + BACKDROP_SIZE + backdropPath;
                Log.d(TAG, "Loading backdrop image: " + fullBackdropUrl);
                Picasso.get()
                        .load(fullBackdropUrl)
                        .placeholder(R.drawable.ic_launcher_background) // Replace with a proper placeholder drawable
                        .error(R.drawable.ic_launcher_foreground)       // Replace with a proper error drawable
                        .into(bannerImage);
            } else {
                Log.w(TAG, "No backdrop path found for item ID.");
                // Optionally load a default image or hide the ImageView
                bannerImage.setImageResource(R.drawable.ic_launcher_foreground); // Set error/placeholder if no banner
            }

        } catch (Exception e) { // Catch generic Exception, though JSONException is common
            Log.e(TAG, "JSON Parsing error: " + e.getMessage(), e);
            movieTitle.setText("Error Parsing");
            movieDescription.setText("Could not read details from the server.");
            Toast.makeText(this, "Error reading details.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Cancel any pending Volley requests if the activity is stopped
        if (queue != null) {
            queue.cancelAll(TAG); // Use tag to cancel requests specific to this activity if needed elsewhere
        }
    }
}
