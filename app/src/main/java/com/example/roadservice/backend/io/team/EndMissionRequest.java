package com.example.roadservice.backend.io.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndMissionRequest {
    @SerializedName("report")
    @Expose
    public String report;

    public EndMissionRequest(String report) {
        this.report = report;
    }
}
