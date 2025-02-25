package com.example.closetifiy_finalproject;

import android.content.Intent; // Ensure this is imported for starting new activities
import android.os.Bundle;
import android.widget.Button; // Ensure this is imported for Button views

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons from the layout
        Button signInButton = findViewById(R.id.signin);
        Button signUpButton = findViewById(R.id.signup);

        // Set onClickListener for the "Sign In" button
        signInButton.setOnClickListener(view -> {
            // Navigate to the SignIn activity
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
        });

        // Set onClickListener for the "Sign Up" button
        signUpButton.setOnClickListener(view -> {
            // Navigate to the SignUp activity
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }
}
