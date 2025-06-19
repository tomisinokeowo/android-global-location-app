package com.example.id22039381.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.id22039381.R;
import com.example.id22039381.database.DatabaseHelper;

/**
 * ViewLocationsActivity displays a list of saved locations and allows sorting based on various criteria.
 */
public class ViewLocationsActivity extends AppCompatActivity {

    private LocationAdapter adapter; // Adapter for managing location data in RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations); // Set the layout for this activity

        // Set up the Toolbar to provide a title and navigation options
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title for the Toolbar dynamically
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("View Locations");
        }

        // Initialize the RecyclerView for displaying the list of locations
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager for vertical list

        // Initialize the adapter and attach it to the RecyclerView
        adapter = new LocationAdapter(this);
        recyclerView.setAdapter(adapter);

        // Load location data from the database
        loadLocations(null); // Load data without any initial sorting

        // Initialize the Spinner for sorting options
        Spinner spinnerSort = findViewById(R.id.spinner_sort);
        spinnerSort.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                // Retrieve the selected sorting option
                String sortBy = parent.getItemAtPosition(position).toString();
                loadLocations(sortBy); // Reload locations based on the selected sorting option
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // No action required when nothing is selected
            }
        });
    }

    /**
     * Loads location data from the database and sorts it based on the specified criteria.
     *
     * @param sortBy The column by which to sort the locations (e.g., "Name", "Rating", "Date").
     */
    private void loadLocations(String sortBy) {
        DatabaseHelper dbHelper = new DatabaseHelper(this); // Initialize database helper
        Cursor cursor;

        // Determine the sorting criteria based on the user's selection
        if ("Name".equals(sortBy)) {
            cursor = dbHelper.getAllLocationsSortedBy("name"); // Sort locations by name
        } else if ("Rating".equals(sortBy)) {
            cursor = dbHelper.getAllLocationsSortedBy("rating"); // Sort locations by rating
        } else if ("Date".equals(sortBy)) {
            cursor = dbHelper.getAllLocationsSortedBy("date_visited"); // Sort locations by date visited
        } else if ("Distance from Current Location".equals(sortBy)) {
            cursor = dbHelper.getAllLocationsSortedBy("distance"); // Sort locations by distance (if implemented)
        } else {
            cursor = dbHelper.getAllLocations(); // Load all locations without sorting
        }

        // Update the adapter with the new data from the cursor
        adapter.setCursor(cursor);
    }
}
