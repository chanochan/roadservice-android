package com.example.roadservice.backend.io.specialist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateMissionResponse {
    public static int CODE = 302;

    @SerializedName("status")
    @Expose
    public boolean status;
}
