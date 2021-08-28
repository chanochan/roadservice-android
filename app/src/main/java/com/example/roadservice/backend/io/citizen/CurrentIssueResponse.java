package com.example.roadservice.backend.io.citizen;

import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentIssueResponse {
    public static final int CODE = 200;
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("state")
    @Expose
    public String state;

    @SerializedName("lat")
    @Expose
    public double latitude;

    @SerializedName("long")
    @Expose
    public double longitude;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    public Issue toIssue() {
        // TODO Image address
        return new Issue(
                new GeoLocation(latitude, longitude),
                title,
                description,
                null
        );
    }
}
