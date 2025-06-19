package com.example.id22039381.models;

/**
 * Represents a Location model, containing details about a specific location.
 * This class is used to store and retrieve location information.
 */
public class Location {

    // Declaring variables to hold location details
    private String name; // Name of the location
    private String country; // Country of the location
    private String gpsCoordinates; // GPS coordinates of the location
    private String dateVisited; // Date when the location was visited
    private int rating; // Rating provided for the location

    /**
     * Constructor for initializing a Location object with specific details.
     *
     * @param name Name of the location
     * @param country Country where the location is situated
     * @param gpsCoordinates GPS coordinates of the location
     * @param dateVisited Date when the location was visited
     * @param rating Rating for the location
     */
    public Location(String name, String country, String gpsCoordinates, String dateVisited, int rating) {
        this.name = name; // Assign the location name
        this.country = country; // Assign the country
        this.gpsCoordinates = gpsCoordinates; // Assign GPS coordinates
        this.dateVisited = dateVisited; // Assign the visit date
        this.rating = rating; // Assign the rating
    }

    // Getter method to retrieve the name of the location
    public String getName() {
        return name;
    }

    // Setter method to update the name of the location
    public void setName(String name) {
        this.name = name;
    }

    // Getter method to retrieve the country of the location
    public String getCountry() {
        return country;
    }

    // Setter method to update the country of the location
    public void setCountry(String country) {
        this.country = country;
    }

    // Getter method to retrieve the GPS coordinates of the location
    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    // Setter method to update the GPS coordinates of the location
    public void setGpsCoordinates(String gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    // Getter method to retrieve the visit date of the location
    public String getDateVisited() {
        return dateVisited;
    }

    // Setter method to update the visit date of the location
    public void setDateVisited(String dateVisited) {
        this.dateVisited = dateVisited;
    }

    // Getter method to retrieve the rating of the location
    public int getRating() {
        return rating;
    }

    // Setter method to update the rating of the location
    public void setRating(int rating) {
        this.rating = rating;
    }
}
