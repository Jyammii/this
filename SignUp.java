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

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        EditText emailInput = findViewById(R.id.EmailSignUp);
        EditText passwordInput = findViewById(R.id.PassSignUp);
        EditText confirmPasswordInput = findViewById(R.id.confirmpass);
        Button signUpButton = findViewById(R.id.btnsignup);
        TextView signInLink = findViewById(R.id.linksignin);

        // Handle "Sign In" link click
        signInLink.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        });

        // Handle "Sign Up" button click
        signUpButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            // Validate email format
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(SignUp.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate password strength
            if (password.length() < 6) {
                Toast.makeText(SignUp.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validation checks
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Save user credentials in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", email);
                editor.putString("Password", password);
                editor.apply();

                // Show success message and navigate to HomeActivity
                Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}