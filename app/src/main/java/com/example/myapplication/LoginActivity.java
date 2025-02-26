package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private ImageView showPasswordIcon;
    private Button signInButton;
    private TextView signUpLink, needHelpLink;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        showPasswordIcon = findViewById(R.id.showPasswordIcon);
        signInButton = findViewById(R.id.signInButton);
        signUpLink = findViewById(R.id.signUpLink);
        needHelpLink = findViewById(R.id.needHelpLink);

        // Show/Hide password functionality
        showPasswordIcon.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPasswordIcon.setImageResource(R.drawable.eye_password_hide_svgrepo_com); // Replace with your eye icon
            } else {
                passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPasswordIcon.setImageResource(R.drawable.eye_password_hide_svgrepo_com); // Replace with your eye-off icon
            }
            isPasswordVisible = !isPasswordVisible;
        });

        // Sign in button action
        signInButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
//            } else {
                // Dummy login (replace with Firebase Authentication)
                if (email.equals("") && password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, profile_picker.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
//            }
        });

        // Navigate to Sign-Up Page
        signUpLink.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        });

        // Need Help? Action
        needHelpLink.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Help Section Coming Soon!", Toast.LENGTH_SHORT).show();
        });
    }
}
