package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize views
        EditText emailInput = findViewById(R.id.SignInEmail);
        EditText passwordInput = findViewById(R.id.SignInPass);
        Button signInButton = findViewById(R.id.buttonsignin);
        TextView signUpLink = findViewById(R.id.signuplink);

        // Handle "Sign Up" link click
        signUpLink.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        // Handle "Sign In" button click
        signInButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Validate email format
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(SignIn.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // Retrieve saved credentials from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String savedEmail = sharedPreferences.getString("Email", null);
            String savedPassword = sharedPreferences.getString("Password", null);

            // Validate login credentials
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignIn.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (email.equals(savedEmail) && password.equals(savedPassword)) {
                Toast.makeText(SignIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignIn.this, Home.class); // Assuming "Home" is the correct class for home screen
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SignIn.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}