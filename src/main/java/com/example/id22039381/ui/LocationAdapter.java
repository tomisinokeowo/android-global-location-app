package com.example.id22039381.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.id22039381.R;
import com.example.id22039381.database.DatabaseHelper;

/**
 * Adapter class for displaying a list of locations in a RecyclerView.
 * Manages the data and binds it to the UI components in the layout.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private Cursor cursor; // Cursor holding the location data from the database
    private final Context context; // Context for accessing resources and utilities

    /**
     * Constructor to initialize the adapter with a context.
     * @param context The context of the calling activity.
     */
    public LocationAdapter(Context context) {
        this.context = context;
    }

    /**
     * Sets the cursor containing location data and notifies the adapter to update the RecyclerView.
     * @param cursor The cursor with the location data.
     */
    public void setCursor(Cursor cursor) {
        this.cursor = cursor; // Assign the new cursor
        notifyDataSetChanged(); // Notify that the data has changed
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single location item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        // Check if the cursor is valid and move it to the current position
        if (cursor != null && cursor.moveToPosition(position)) {
            // Retrieve location details from the cursor
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String country = cursor.getString(cursor.getColumnIndex("country"));
            @SuppressLint("Range") String gps = cursor.getString(cursor.getColumnIndex("gps_coordinates"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date_visited"));
            @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex("rating"));
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));

            // Bind the retrieved data to the corresponding views in the ViewHolder
            holder.tvName.setText(name); // Set the location name
            holder.tvCountry.setText(country); // Set the country name
            holder.tvDetails.setText("GPS: " + gps + ", Date: " + date + ", Rating: " + rating); // Set detailed info

            // Handle the click event for the delete button
            holder.btnDelete.setOnClickListener(v -> {
                DatabaseHelper dbHelper = new DatabaseHelper(context); // Initialize the database helper
                // Attempt to delete the location using its ID
                int rowsDeleted = dbHelper.deleteLocation(id);
                if (rowsDeleted > 0) {
                    // Show a success message if deletion is successful
                    Toast.makeText(context, "Location deleted!", Toast.LENGTH_SHORT).show();
                    // Refresh the cursor to reflect the updated data
                    Cursor updatedCursor = dbHelper.getAllLocations();
                    setCursor(updatedCursor); // Update the adapter's cursor
                } else {
                    // Show an error message if deletion fails
                    Toast.makeText(context, "Failed to delete location.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the cursor
        return (cursor == null) ? 0 : cursor.getCount();
    }

    /**
     * ViewHolder class to manage the views for a single location item.
     * Holds references to the UI components for improved performance.
     */
    static class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCountry, tvDetails; // TextViews for displaying location details
        ImageButton btnDelete; // Button for deleting a location

        /**
         * Constructor to initialize the ViewHolder's components.
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            tvName = itemView.findViewById(R.id.tv_name); // TextView for the location name
            tvCountry = itemView.findViewById(R.id.tv_country); // TextView for the country name
            tvDetails = itemView.findViewById(R.id.tv_details); // TextView for GPS, date, and rating
            btnDelete = itemView.findViewById(R.id.btn_delete); // Button for deleting the location
        }
    }
}
