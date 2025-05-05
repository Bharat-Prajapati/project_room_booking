package com.example.apnaroom.Domains;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemsModel implements Serializable {
    private int id;
    private String name;
    private String location;
    private double price_per_night;
    private boolean availability;
    private String description;
    private ArrayList<String> amenities;
    private String image_url;
    private double rating;
    private int reviews;
    private double price_per_month;

    public ItemsModel() {
    }

    public ItemsModel(int id, String name, String location, double price_per_night, boolean availability, String description, ArrayList<String> amenities, String image_url, double rating, int reviews, double price_per_month) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price_per_night = price_per_night;
        this.availability = availability;
        this.description = description;
        this.amenities = amenities;
        this.image_url = image_url;
        this.rating = rating;
        this.reviews = reviews;
        this.price_per_month = price_per_month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(double price_per_night) {
        this.price_per_night = price_per_night;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public double getPrice_per_month(){
        return price_per_month;
    }

    public void setPrice_per_month(){
        this.price_per_month = price_per_month;
    }
}
