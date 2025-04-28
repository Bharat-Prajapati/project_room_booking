package com.example.apnaroom.Domains;

import java.io.Serializable;
import java.util.ArrayList;

public class PopularModel implements Serializable{
    private int room_id;
    private String room_name;
    private String room_type;
    private int price_per_night;
    private boolean availability;
    private String description;
    private ArrayList<String> amenities;
    private String imageUrl;

    public PopularModel(){

    }

    public PopularModel(int room_id, String room_name, String room_type, int price_per_night, boolean availability, String description, ArrayList<String> amenities, String imageUrl) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.room_type = room_type;
        this.price_per_night = price_per_night;
        this.availability = availability;
        this.description = description;
        this.amenities = amenities;
        this.imageUrl = imageUrl;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(int price_per_night) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
