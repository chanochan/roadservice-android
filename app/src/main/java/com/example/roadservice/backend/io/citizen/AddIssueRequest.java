package com.example.roadservice.backend.io.citizen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class AddIssueRequest {
    @SerializedName("lat")
    @Expose
    public String latitude;

    @SerializedName("long")
    @Expose
    public String longitude;

    @SerializedName("county")
    @Expose
    public int countyId;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("base64_image")
    @Expose
    public String encodedImage;

    public AddIssueRequest(String title, String description, int countyId, double latitude,
                           double longitude, String encodedImage) {
        this.latitude = String.format(Locale.getDefault(), "%06f", latitude);
        this.longitude = String.format(Locale.getDefault(), "%06f", longitude);
        this.countyId = countyId;
        this.title = title;
        this.description = description;
        this.encodedImage = "data:image/png;base64," + encodedImage;
    }
}
