package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class languageActivity extends AppCompatActivity {

    private Button btnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale(); // Apply saved language before UI loads
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);

        btnLanguage = findViewById(R.id.btn_language);

        // Show popup menu on button click
        btnLanguage.setOnClickListener(this::showPopupMenu);

        ImageButton n = findViewById(R.id.imageButton7);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(languageActivity.this, "Home", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(languageActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        ImageButton h = findViewById(R.id.imageButton8);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(languageActivity.this, "Search", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(languageActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageButton d = findViewById(R.id.imageButton9);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(languageActivity.this, "Downloads", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(languageActivity.this,DownloadsActivity.class);
                startActivity(intent);
            }
        });
        ImageButton p = findViewById(R.id.imageButton10);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(languageActivity.this, "User Settings", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(languageActivity.this,languageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());

        // Handle menu item clicks
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.lang_english) {
                setLocale("en");
            } else if (item.getItemId() == R.id.lang_spanish) {
                setLocale("es");
            } else if (item.getItemId() == R.id.lang_french) {
                setLocale("fr");
            } else if (item.getItemId() == R.id.lang_hindi){
                setLocale("hi");
            }
            return true;
        });

        popup.show();
    }

    private void setLocale(String langCode) {
        // Update the app's locale
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Save the selected language in SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("App_Lang", langCode);
        editor.apply();

        // Restart the activity to apply changes
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("App_Lang", "en"); // Default to English
        updateLocale(language);
    }

    private void updateLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}


