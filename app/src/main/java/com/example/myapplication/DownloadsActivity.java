package com.example.myapplication;

import static com.example.myapplication.R.*;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class DownloadsActivity extends AppCompatActivity {
    // This class is currently empty, but you can add functionality as needed.
    // For example, you might want to implement a download manager or a list of downloaded items.
    // You can also create a layout file for this activity and set it in onCreate() method.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);
        // Initialize your views and set up any necessary functionality here.
        ImageButton n = findViewById(id.imageButton7);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadsActivity.this, "Home", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DownloadsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        ImageButton h = findViewById(R.id.imageButton8);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadsActivity.this, "Search", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DownloadsActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageButton d = findViewById(R.id.imageButton9);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadsActivity.this, "Downloads", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DownloadsActivity.this,DownloadsActivity.class);
                startActivity(intent);
            }
        });
        ImageButton pro = findViewById(R.id.imageButton10);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadsActivity.this, "User Settings", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DownloadsActivity.this,languageActivity.class);
                startActivity(intent);
            }
        });
    }
}
