package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

public class DescriptionActivity extends AppCompatActivity {

    private static final String API_KEY = "f339571cd5d90bc05b2b32cd85fa6e11"; // Replace with your TMDb API key
    private static final String TAG = "DescriptionActivity";

    private ImageView bannerImage;
    private TextView movieTitle;
    private TextView movieDescription;
    private ImageButton playButton, myListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        bannerImage = findViewById(R.id.bannerImage);
        movieTitle = findViewById(R.id.movieTitle);
        movieDescription = findViewById(R.id.movieDescription);
//        playButton = findViewById(R.id.playButton);
//        myListButton = findViewById(R.id.myListButton);

        // Get movie ID from Intent
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("ITEM_ID", -1);

        if (movieId != -1) {
            fetchMovieDetails(movieId);
        } else {
            Log.e(TAG, "Invalid movie ID");
        }

        // Set click listeners for buttons (You can implement these later)
//        playButton.setOnClickListener(v -> {
//            // Start playback or navigate to the player screen
//        });

//        myListButton.setOnClickListener(v -> {
//            // Add the movie/TV show to the user's list
//        });
    }

    private void fetchMovieDetails(int movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY + "&language=en-US";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        String title = response.getString("title");
                        String overview = response.getString("overview");
                        String bannerPath = response.getString("backdrop_path");

                        // Update UI
                        movieTitle.setText(title);
                        movieDescription.setText(overview);
                        Picasso.get().load("https://image.tmdb.org/t/p/w780" + bannerPath).into(bannerImage);

                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                    }
                },
                error -> Log.e(TAG, "Volley error: " + error.getMessage())
        );

        queue.add(jsonObjectRequest);
    }
}
