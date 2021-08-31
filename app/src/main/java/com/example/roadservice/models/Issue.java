package com.example.roadservice.models;

public class Issue {
    private int id;
    private GeoLocation location;
    private String title;
    private String description;
    private String imageAddress;
    private int county;

    public Issue(int id, GeoLocation location, String title, String description, String imageAddress, int county) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.description = description;
        this.imageAddress = imageAddress;
        this.county = county;
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

    public int getCounty() {
        return county;
    }

    public void setCounty(int county) {
        this.county = county;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
