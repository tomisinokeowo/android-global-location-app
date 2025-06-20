package com.example.id22039381.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.id22039381.R;
import com.example.id22039381.database.DatabaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Activity for adding a new location.
 * Handles user input, location fetching, and database operations.
 */
public class AddLocationActivity extends AppCompatActivity {

    // Constant to identify the location permission request
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Client for accessing the device's location
    private FusedLocationProviderClient fusedLocationProviderClient;

    // Input field for GPS coordinates
    private EditText etGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location); // Set the layout for this activity

        // Initialize UI components
        EditText etName = findViewById(R.id.et_name); // Input for location name
        etGps = findViewById(R.id.et_gps); // Input for GPS coordinates
        EditText etCountry = findViewById(R.id.et_country); // Input for country
        EditText etDate = findViewById(R.id.et_date); // Input for date visited
        EditText etRating = findViewById(R.id.et_rating); // Input for rating
        Button btnFetchGps = findViewById(R.id.btn_get_current_gps_location); // Button to fetch GPS
        Button btnSubmit = findViewById(R.id.btn_submit); // Button to submit data

        // Initialize the location client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Listener for the "Get Current GPS" button
        btnFetchGps.setOnClickListener(v -> {
            // Check if location permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                // Fetch the current GPS location
                fetchCurrentLocation();
            }
        });

        // Listener for the "Submit" button
        btnSubmit.setOnClickListener(v -> {
            // Retrieve user inputs
            String name = etName.getText().toString();
            String country = etCountry.getText().toString();
            String gps = etGps.getText().toString();
            String date = etDate.getText().toString();
            String ratingText = etRating.getText().toString();

            // Validate that all fields are filled
            if (name.isEmpty() || country.isEmpty() || gps.isEmpty() || date.isEmpty() || ratingText.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return; // Exit if validation fails
            }

            // Validate that the rating is a valid integer
            int rating;
            try {
                rating = Integer.parseInt(ratingText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Rating must be a number!", Toast.LENGTH_SHORT).show();
                return; // Exit if rating is invalid
            }

            // Ensure rating is between 1 and 10
            if (rating < 1 || rating > 10) {
                Toast.makeText(this, "Rating must be between 1 and 10!", Toast.LENGTH_SHORT).show();
                return; // Exit if rating is out of range
            }

            // Add location to the database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            long id = dbHelper.addLocation(name, country, gps, date, rating);

            // Display success or failure message
            if (id > 0) {
                Toast.makeText(this, "Location added!", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity on success
            } else {
                Toast.makeText(this, "Failed to add location!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Handle the result of the location permission request
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Fetch location if permission is granted
                fetchCurrentLocation();
            } else {
                // Show error if permission is denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Fetches the device's current location using the Fused Location Provider.
     */
    private void fetchCurrentLocation() {
        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
            return; // Exit if permission is missing
        }

        // Configure the location request for high accuracy
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // 10-second intervals
        locationRequest.setFastestInterval(5000); // Fastest update at 5 seconds

        // Request location updates
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                // Handle the received location data
                if (locationResult == null) {
                    Toast.makeText(AddLocationActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    return; // Exit if no location is found
                }
                for (Location location : locationResult.getLocations()) {
                    etGps.setText(location.getLatitude() + ", " + location.getLongitude()); // Update GPS field
                }
            }
        }, Looper.getMainLooper()); // Use the main thread for updates
    }
}
