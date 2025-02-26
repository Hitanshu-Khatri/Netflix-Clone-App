package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply SplashScreen API for Android 12+
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find VideoView
        VideoView videoView = findViewById(R.id.videoView);

        // Load Netflix intro video from raw folder
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.netflix_intro2;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Start video
        videoView.start();

        // After video completes, go to LoginActivity
        videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
