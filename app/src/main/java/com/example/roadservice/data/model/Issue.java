package com.example.roadservice.data.model;

public class Issue {
    private GeoLocation location;
    private String title;
    private String description;

    public Issue(GeoLocation location, String title, String description) {
        this.location = location;
        this.title = title;
        this.description = description;
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
}
