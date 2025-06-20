package com.example.id22039381.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database configuration constants
    private static final String DATABASE_NAME = "locations.db"; // Name of the database
    private static final int DATABASE_VERSION = 1; // Version of the database

    // Table and column definitions
    private static final String TABLE_LOCATIONS = "locations"; // Name of the table
    private static final String COLUMN_ID = "id"; // Primary key column
    private static final String COLUMN_NAME = "name"; // Column for location name
    private static final String COLUMN_COUNTRY = "country"; // Column for country name
    private static final String COLUMN_GPS = "gps_coordinates"; // Column for GPS coordinates
    private static final String COLUMN_DATE = "date_visited"; // Column for the visit date
    private static final String COLUMN_RATING = "rating"; // Column for the location rating

    // SQL command to create the locations table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-incrementing ID
            COLUMN_NAME + " TEXT, " + // Name of the location
            COLUMN_COUNTRY + " TEXT, " + // Country of the location
            COLUMN_GPS + " TEXT, " + // GPS coordinates
            COLUMN_DATE + " TEXT, " + // Date visited
            COLUMN_RATING + " INTEGER)"; // User-provided rating

    /**
     * Constructor for initializing the database helper.
     * @param context The application context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the locations table when the database is first created.
     * @param db The SQLite database instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // Execute the SQL command to create the table
    }

    /**
     * Handles database upgrades by dropping the old table and creating a new one.
     * @param db The SQLite database instance.
     * @param oldVersion The old version of the database.
     * @param newVersion The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS); // Delete existing table
        onCreate(db); // Recreate the table with the updated schema
    }

    /**
     * Adds a new location to the database.
     * @param name Name of the location.
     * @param country Country of the location.
     * @param gps GPS coordinates of the location.
     * @param date Date the location was visited.
     * @param rating User-provided rating for the location.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long addLocation(String name, String country, String gps, String date, int rating) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        ContentValues values = new ContentValues(); // Store key-value pairs
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_GPS, gps);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_RATING, rating);
        return db.insert(TABLE_LOCATIONS, null, values); // Insert data into the table
    }

    /**
     * Retrieves all stored locations from the database.
     * @return A cursor pointing to the results of the query.
     */
    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database
        return db.query(TABLE_LOCATIONS, null, null, null, null, null, COLUMN_NAME + " ASC"); // Return all rows sorted by name
    }

    /**
     * Deletes a specific location from the database using its ID.
     * @param id The unique ID of the location to be deleted.
     * @return The number of rows affected by the deletion.
     */
    public int deleteLocation(long id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        return db.delete(TABLE_LOCATIONS, COLUMN_ID + "=?", new String[]{String.valueOf(id)}); // Delete the row matching the ID
    }

    /**
     * Retrieves all locations sorted by the specified column.
     * @param column The column to sort by (e.g., name, rating, or date).
     * @return A cursor pointing to the sorted results.
     * @throws IllegalArgumentException if the column name is invalid.
     */
    public Cursor getAllLocationsSortedBy(String column) {
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database

        // Validate the column name to avoid SQL injection attacks
        if (!column.equals(COLUMN_NAME) && !column.equals(COLUMN_RATING) && !column.equals(COLUMN_DATE)) {
            throw new IllegalArgumentException("Invalid column name for sorting: " + column);
        }

        // Return all rows sorted by the specified column
        return db.query(TABLE_LOCATIONS, null, null, null, null, null, column + " ASC");
    }
}
