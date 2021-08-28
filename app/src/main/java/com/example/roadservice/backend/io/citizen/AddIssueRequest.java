package com.example.roadservice.backend.io.citizen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddIssueRequest {
    @SerializedName("lat")
    @Expose
    public double latitude;

    @SerializedName("long")
    @Expose
    public double longitude;

    @SerializedName("county")
    @Expose
    public int countyId;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    // TODO image

    public AddIssueRequest(String title, String description, int countyId, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.countyId = countyId;
        this.title = title;
        this.description = description;
    }
}
