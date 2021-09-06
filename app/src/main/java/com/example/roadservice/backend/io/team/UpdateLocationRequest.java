package com.example.roadservice.backend.io.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class UpdateLocationRequest {
    @SerializedName("lat")
    @Expose
    private final String latitude;

    @SerializedName("long")
    @Expose
    private final String longitude;

    public UpdateLocationRequest(double latitude, double longitude) {
        this.latitude = String.format(Locale.ENGLISH, "%06f", latitude);
        this.longitude = String.format(Locale.ENGLISH, "%06f", longitude);
    }
}
