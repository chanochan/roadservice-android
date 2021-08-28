package com.example.roadservice.models;

public class Issue {
    private GeoLocation location;
    private String title;
    private String description;
    private String imageAddress;

    public Issue(GeoLocation location, String title, String description, String imageAddress) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.imageAddress = imageAddress;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
