package com.example.roadservice.backend.io.basics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusResponse {
    @SerializedName("status")
    @Expose
    public boolean status;
}
