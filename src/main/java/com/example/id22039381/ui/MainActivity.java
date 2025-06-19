package com.example.id22039381.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.id22039381.R;

/**
 * MainActivity serves as the entry point of the application.
 * It provides navigation to other activities, such as adding a location or viewing saved locations.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for the main activity

        // Initialize the Toolbar and set it as the app's action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title for the toolbar dynamically
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Location App");
        }

        // Initialize buttons for navigating to different activities
        Button btnAddLocation = findViewById(R.id.btn_add_location); // Button to add a new location
        Button btnViewLocations = findViewById(R.id.btn_view_locations); // Button to view saved locations

        // Set a click listener for the "Add Location" button
        btnAddLocation.setOnClickListener(v -> {
            // Create an intent to start the AddLocationActivity
            Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
            startActivity(intent); // Launch the AddLocationActivity
        });

        // Set a click listener for the "View Locations" button
        btnViewLocations.setOnClickListener(v -> {
            // Create an intent to start the ViewLocationsActivity
            Intent intent = new Intent(MainActivity.this, ViewLocationsActivity.class);
            startActivity(intent); // Launch the ViewLocationsActivity
        });

    }
}
