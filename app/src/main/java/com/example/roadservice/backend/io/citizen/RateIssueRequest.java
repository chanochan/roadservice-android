package com.example.roadservice.backend.io.citizen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateIssueRequest {
    @SerializedName("rating")
    @Expose
    public int rating;

    public RateIssueRequest(int rating) {
        this.rating = rating;
    }
}
