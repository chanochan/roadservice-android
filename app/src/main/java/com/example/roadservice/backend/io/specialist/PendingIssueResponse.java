package com.example.roadservice.backend.io.specialist;

import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingIssueResponse {
    public static final int CODE = 300;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("lat")
    @Expose
    public double latitude;

    @SerializedName("long")
    @Expose
    public double longitude;

    @SerializedName("state")
    @Expose
    public String state;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("county")
    @Expose
    public int countyId;

    public Issue toIssue() {
        return new Issue(
                new GeoLocation(latitude, longitude),
                title,
                description,
                null,
                countyId
        );
    }
}
