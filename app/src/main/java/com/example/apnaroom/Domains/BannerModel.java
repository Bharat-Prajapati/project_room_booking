package com.example.apnaroom.Domains;

public class BannerModel {
    private int id;
    private String imageUrl;

    public BannerModel() {
    }

    public BannerModel(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
