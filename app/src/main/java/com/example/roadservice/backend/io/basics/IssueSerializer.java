package com.example.roadservice.backend.io.basics;

import com.example.roadservice.backend.RetrofitInstance;
import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueSerializer {
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

    @SerializedName("image_url")
    @Expose
    public String imageUrl;

    @SerializedName("county")
    @Expose
    public int countyId;

    public Issue toIssue() {
        return new Issue(
                id,
                new GeoLocation(latitude, longitude),
                title,
                description,
                (imageUrl == null ? null : RetrofitInstance.BASE_URL + imageUrl.substring(1)),
                countyId
        );
    }
}
