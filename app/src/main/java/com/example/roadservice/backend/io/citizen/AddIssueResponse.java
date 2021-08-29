package com.example.roadservice.backend.io.citizen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddIssueResponse {
    public static final int CODE = 201;

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

    @SerializedName("county")
    @Expose
    public int county;
}
