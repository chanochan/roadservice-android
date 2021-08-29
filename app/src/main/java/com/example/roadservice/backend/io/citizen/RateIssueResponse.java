package com.example.roadservice.backend.io.citizen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateIssueResponse {
    public static final int CODE = 202;

    @SerializedName("status")
    @Expose
    public boolean status;
}
