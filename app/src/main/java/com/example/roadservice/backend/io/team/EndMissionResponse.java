package com.example.roadservice.backend.io.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndMissionResponse {
    public static final int CODE = 402;

    @SerializedName("status")
    @Expose
    public boolean status;
}
